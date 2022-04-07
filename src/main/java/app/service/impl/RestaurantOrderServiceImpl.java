package app.service.impl;

import app.dto.RestaurantOrderDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidOperationException;
import app.mapper.RestaurantOrderMapper;
import app.model.*;
import app.model.order_states.AbstractOrderState;
import app.model.order_states.OrderStatusManagerFactory;
import app.model.order_states.PendingState;
import app.repository.*;
import app.service.api.RestaurantOrderService;
import app.service.validator.RestaurantOrderDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static app.model.OrderStatus.*;

@Service
public class RestaurantOrderServiceImpl implements RestaurantOrderService {

    private static final String INEXISTENT_CART_ERROR_MESSAGE = "The order cannot be created! The user has no cart!";
    private static final String INEXISTENT_ORDER_ERROR_MESSAGE = "No order with the given orderNumber has been found!";
    private static final String EMPTY_CART_ERROR_MESSAGE = "The order cannot be created! No foods are present in the cart!";
    private static final String MULTIPLE_RESTAURANT_ERROR_MESSAGE = "Invalid data! Cannot order from multiple restaurants at the same time!";

    @Autowired
    private RestaurantOrderRepository restaurantOrderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestaurantOrderMapper restaurantOrderMapper;

    @Autowired
    private RestaurantOrderDataValidator restaurantOrderDataValidator;

    @Autowired
    private OrderStatusManagerFactory orderStatusManagerFactory;

    @Override
    public List<String> getAllOrderStatuses() {
        return Arrays.stream(values()).map(Enum::name).collect(Collectors.toList());
    }

    @Override
    public RestaurantOrderDto getOrderByOrderNumber(String orderNumber) throws EntityNotFoundException {
        return restaurantOrderMapper.toDto(
                restaurantOrderRepository.findByOrderNumber(orderNumber)
                        .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE)));
    }

    @Override
    public List<RestaurantOrderDto> getOrdersOfCustomerByStatus(String customer, Optional<String> orderStatus) {
        return ((orderStatus.isPresent()) ? restaurantOrderRepository.findByUserAndStatus(customer, OrderStatus.valueOf(orderStatus.get())) : restaurantOrderRepository.findByUser(customer))
                .stream()
                .map(restaurantOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantOrderDto> getOrdersOfRestaurantByStatus(String restaurantName, Optional<String> orderStatus) {
        return orderStatus.map(status -> restaurantOrderRepository.findByRestaurantAndStatus(restaurantName, OrderStatus.valueOf(status)))
                .orElseGet(() -> restaurantOrderRepository.findByRestaurant(restaurantName))
                .stream()
                .map(restaurantOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addOrder(RestaurantOrderDto restaurantOrderDto) throws InvalidDataException {
        restaurantOrderDataValidator.validate(restaurantOrderDto);

        Cart cart = cartRepository.findByCustomer(restaurantOrderDto.getCustomer())
                .orElseThrow(() -> new InvalidDataException(INEXISTENT_CART_ERROR_MESSAGE));

        if (cart.getFoods().isEmpty()) {
            throw new InvalidDataException(EMPTY_CART_ERROR_MESSAGE);
        }

        RestaurantOrder restaurantOrder = restaurantOrderMapper.toEntity(restaurantOrderDto);
        String orderNumber = String.valueOf(new Random().nextInt(900000) + 100000);
        restaurantOrder.setOrderNumber(orderNumber);
        restaurantOrder.setOrderStatus(PENDING);
        restaurantOrder.setDateCreated(LocalDate.now());

        long nrOfDifferentRestaurants = cart.getFoods()
                .keySet()
                .stream()
                .map(Food::getRestaurant)
                .map(Restaurant::getName)
                .distinct()
                .count();

        if (nrOfDifferentRestaurants != 1) {
            throw new InvalidDataException(MULTIPLE_RESTAURANT_ERROR_MESSAGE);
        }

        Restaurant restaurant = cart.getFoods()
                .keySet()
                .stream()
                .map(Food::getRestaurant)
                .findFirst()
                .get();

        restaurantOrder.setRestaurant(restaurant);
        restaurantOrder.addOrderedFoods(cart.getFoods());
        restaurantOrder.computeTotalPrice();

        restaurantOrder.getCustomer().addOrder(restaurantOrder);
        restaurantOrder.getRestaurant().addOrder(restaurantOrder);

        restaurantOrderRepository.save(restaurantOrder);

        cart.deleteAllFood();
        cartRepository.save(cart);
    }

    @Override
    public void acceptOrder(String orderNumber) {
        RestaurantOrder restaurantOrder = restaurantOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE));

        orderStatusManagerFactory.createOrderStatusManager(restaurantOrder).accept();
        restaurantOrderRepository.save(restaurantOrder);
    }

    @Override
    public void declineOrder(String orderNumber) throws EntityNotFoundException {
        RestaurantOrder restaurantOrder = restaurantOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE));

        orderStatusManagerFactory.createOrderStatusManager(restaurantOrder).decline();
        restaurantOrderRepository.save(restaurantOrder);
    }

    @Override
    public void setOrderDelivered(String orderNumber) throws EntityNotFoundException {
        RestaurantOrder restaurantOrder = restaurantOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE));

        orderStatusManagerFactory.createOrderStatusManager(restaurantOrder).setDelivered();
        restaurantOrderRepository.save(restaurantOrder);
    }

    @Override
    public void setOrderInDelivery(String orderNumber) throws EntityNotFoundException {
        RestaurantOrder restaurantOrder = restaurantOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE));

        orderStatusManagerFactory.createOrderStatusManager(restaurantOrder).setInDelivery();
        restaurantOrderRepository.save(restaurantOrder);
    }

    @Override
    public List<String> getAvailableStatusForOrder(String orderNumber) {
        RestaurantOrder restaurantOrder = restaurantOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE));
        AbstractOrderState orderState = orderStatusManagerFactory.createOrderStatusManager(restaurantOrder);

        return orderState.getNextStates()
                .stream()
                .map(OrderStatus::name)
                .collect(Collectors.toList());
    }
}
