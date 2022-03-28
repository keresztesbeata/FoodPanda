package app.service.impl;

import app.dto.RestaurantOrderDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.RestaurantOrderMapper;
import app.model.*;
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
    private FoodRepository foodRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestaurantOrderMapper restaurantOrderMapper;

    @Autowired
    private RestaurantOrderDataValidator restaurantOrderDataValidator;

    @Override
    public RestaurantOrderDto getOrderByOrderNumber(String orderNumber) throws EntityNotFoundException {
        return restaurantOrderMapper.toDto(
                restaurantOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE)));
    }

    @Override
    public List<RestaurantOrderDto> getOrdersOfUserByStatus(String username, Optional<String> orderStatus) {
        return orderStatus.map(status -> restaurantOrderRepository.findByUserAndStatus(username, status))
                .orElseGet(() -> restaurantOrderRepository.findByUser(username))
                .stream()
                .map(restaurantOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantOrderDto> getOrdersOfRestaurantByStatus(String restaurantName, Optional<String> orderStatus) {
        return orderStatus.map(status -> restaurantOrderRepository.findByRestaurantAndStatus(restaurantName, status))
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

        if(cart.getFoods().isEmpty()) {
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
                .distinct()
                .count();

        if(nrOfDifferentRestaurants != 1) {
            throw new InvalidDataException(MULTIPLE_RESTAURANT_ERROR_MESSAGE);
        }

        restaurantOrder.addOrderedFoods(cart.getFoods());
        restaurantOrder.computeTotalPrice();

        restaurantOrder.getCustomer().addOrder(restaurantOrder);
        restaurantOrder.getRestaurant().addOrder(restaurantOrder);

        restaurantOrderRepository.save(restaurantOrder);

        cart.deleteAllFood();
        cartRepository.save(cart);
    }

    @Override
    public void updateOrderStatus(String orderNumber, String orderStatus) {
        RestaurantOrder restaurantOrder = restaurantOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE));
        restaurantOrder.setOrderStatus(OrderStatus.valueOf(orderStatus));

        restaurantOrderRepository.save(restaurantOrder);
    }

    @Override
    public List<String> getAvailableStatusForOrder(String orderNumber) {
        RestaurantOrder restaurantOrder = restaurantOrderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE));

        return getNextOrderStatuses(restaurantOrder.getOrderStatus())
                .stream()
                .map(OrderStatus::name)
                .collect(Collectors.toList());
    }

    private List<OrderStatus> getNextOrderStatuses(OrderStatus currentStatus) {
        switch(currentStatus){
            case PENDING: {
                return Arrays.asList(ACCEPTED, DECLINED);
            }
            case ACCEPTED: {
                return Arrays.asList(IN_DELIVERY);
            }
            case IN_DELIVERY: {
                return Arrays.asList(DELIVERED);
            }
            default:
                return Collections.emptyList();
        }
    }
}
