package app.service.impl;

import app.dto.RestaurantOrderDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.RestaurantOrderMapper;
import app.model.*;
import app.model.order_states.AbstractOrderState;
import app.model.order_states.OrderStateFactory;
import app.repository.CartRepository;
import app.repository.RestaurantOrderRepository;
import app.service.api.RestaurantOrderService;
import app.service.validator.RestaurantOrderDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static app.model.OrderStatus.PENDING;
import static app.model.OrderStatus.values;

@Service
public class RestaurantOrderServiceImpl implements RestaurantOrderService {

    private static final String INEXISTENT_CART_ERROR_MESSAGE = "The order cannot be created! The user has no cart!";
    private static final String INEXISTENT_ORDER_ERROR_MESSAGE = "No order with the given orderNumber has been found!";
    private static final String EMPTY_CART_ERROR_MESSAGE = "The order cannot be created! No foods are present in the cart!";
    private static final String MULTIPLE_RESTAURANT_ERROR_MESSAGE = "Invalid data! Cannot order from multiple restaurants at the same time!";
    private static final String STATE_UNCHANGED_ERROR_MESSAGE = "The state of the order is unchanged!";

    @Autowired
    private RestaurantOrderRepository restaurantOrderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private RestaurantOrderMapper restaurantOrderMapper;
    private RestaurantOrderDataValidator restaurantOrderDataValidator = new RestaurantOrderDataValidator();
    private BillGenerator billGenerator = new BillGenerator();
    private OrderStateFactory orderStateFactory = new OrderStateFactory();

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
    public List<RestaurantOrderDto> getOrdersOfCustomerByStatus(User user, Optional<String> orderStatus) {
        return ((orderStatus.isPresent()) ? restaurantOrderRepository.findByUserAndStatus(user.getUsername(), OrderStatus.valueOf(orderStatus.get())) : restaurantOrderRepository.findByUser(user.getUsername()))
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

        RestaurantOrder savedOrder = restaurantOrderRepository.save(restaurantOrder);
        billGenerator.generateBill(savedOrder);

        cart.deleteAllFood();
        cartRepository.save(cart);
    }

    @Override
    public void updateOrderStatus(String orderNumber, String orderStatus) throws EntityNotFoundException, IllegalStateException {
        RestaurantOrder restaurantOrder = restaurantOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE));

        if (OrderStatus.valueOf(orderStatus).equals(restaurantOrder.getOrderStatus())) {
            throw new IllegalStateException(STATE_UNCHANGED_ERROR_MESSAGE);
        }

        AbstractOrderState orderState = orderStateFactory.getOrderState(restaurantOrder);
        switch (OrderStatus.valueOf(orderStatus)) {
            case ACCEPTED: {
                orderState.accept();
                break;
            }
            case DECLINED: {
                orderState.decline();
                break;
            }
            case IN_DELIVERY: {
                orderState.setInDelivery();
                break;
            }
            case DELIVERED: {
                orderState.setDelivered();
                break;
            }
            case PENDING: {
                orderState.resetToPending();
                break;
            }
            default: {
                break;
            }
        }
        restaurantOrderRepository.save(orderState.getOrder());
    }
}
