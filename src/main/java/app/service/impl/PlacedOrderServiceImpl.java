package app.service.impl;

import app.dto.PlacedOrderDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.PlacedOrderMapper;
import app.model.*;
import app.repository.*;
import app.service.api.PlacedOrderService;
import app.service.validator.PlacedOrderValidator;
import app.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static app.model.OrderStatus.*;

@Service
public class PlacedOrderServiceImpl implements PlacedOrderService {

    private static final String INVALID_USER_ERROR_MESSAGE = "The order cannot be created! Invalid user!";
    private static final String INVALID_RESTAURANT_ERROR_MESSAGE = "The order cannot be created! Invalid restaurant!";
    private static final String INEXISTENT_CART_ERROR_MESSAGE = "The order cannot be created! The user has no cart!";
    private static final String INEXISTENT_ORDER_ERROR_MESSAGE = "No order with the given orderNumber has been found!";
    private static final String EMPTY_CART_ERROR_MESSAGE = "The order cannot be created! No foods are present in the cart!";

    @Autowired
    private PlacedOrderRepository placedOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private final PlacedOrderMapper placedOrderMapper = new PlacedOrderMapper();
    private final Validator<PlacedOrderDto> placedOrderValidator = new PlacedOrderValidator();

    @Override
    public List<PlacedOrderDto> getAllPlacedOrders() {
        return placedOrderRepository.findAll()
                .stream()
                .map(placedOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlacedOrderDto getPlacedOrderByOrderNumber(Integer orderNumber) throws EntityNotFoundException {
        return placedOrderMapper.toDto(
                placedOrderRepository.findById(orderNumber)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_ORDER_ERROR_MESSAGE)));
    }

    @Override
    public List<PlacedOrderDto> getPlacedOrdersOfUserByStatus(String username, Optional<String> orderStatus) {
        return orderStatus.map(status -> placedOrderRepository.findPlacedOrderByUserAndStatus(username, status))
                .orElseGet(() -> placedOrderRepository.findPlacedOrderByUser(username))
                .stream()
                .map(placedOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlacedOrderDto> getPlacedOrdersOfRestaurantByStatus(String restaurantName, Optional<String> orderStatus) {
        return orderStatus.map(status -> placedOrderRepository.findPlacedOrderByRestaurantAndStatus(restaurantName, status))
                .orElseGet(() -> placedOrderRepository.findPlacedOrderByRestaurant(restaurantName))
                .stream()
                .map(placedOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addOrder(PlacedOrderDto placedOrderDto) throws InvalidDataException {
        placedOrderValidator.validate(placedOrderDto);

        Restaurant restaurant = restaurantRepository.findByName(placedOrderDto.getRestaurant())
                .orElseThrow(() -> new InvalidDataException(INVALID_RESTAURANT_ERROR_MESSAGE));

        User user = userRepository.findByUsername(placedOrderDto.getUser())
                .orElseThrow(() -> new InvalidDataException(INVALID_USER_ERROR_MESSAGE));

        Cart cart = cartRepository.findByUserNotCompleted(user.getUsername())
                .orElseThrow(() -> new InvalidDataException(INEXISTENT_CART_ERROR_MESSAGE));

        if(cart.getFoods().isEmpty()) {
            throw new InvalidDataException(EMPTY_CART_ERROR_MESSAGE);
        }

        PlacedOrder placedOrder = new PlacedOrder();

        placedOrder.setUser(user);
        placedOrder.setAddress(user.getAddress());
        placedOrder.setOrderDate(LocalDate.now());
        placedOrder.setOrderStatus(OrderStatus.PENDING);
        placedOrder.setRestaurant(restaurant);
        placedOrder.setWithCutlery(placedOrderDto.isWithCutlery());
        placedOrder.setRemark(placedOrderDto.getRemark());
        placedOrder.setCart(cart);
        placedOrder.computeTotalPrice();

        user.addPlacedOrder(placedOrder);
        cart.setCompleted(true);
        cart.setPlacedOrder(placedOrder);
        restaurant.addPlacedOrder(placedOrder);

        placedOrderRepository.save(placedOrder);

        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setFoods(new HashMap<>());

        cartRepository.save(newCart);
    }

    @Override
    public void updateOrderStatus(Integer orderNumber, String orderStatus) {
        PlacedOrder placedOrder = placedOrderRepository.getById(orderNumber);
        placedOrder.setOrderStatus(OrderStatus.valueOf(orderStatus));

        placedOrderRepository.save(placedOrder);
    }

    @Override

    public List<String> getAvailableStatusForOrder(Integer orderNumber) {
        PlacedOrder placedOrder = placedOrderRepository.getById(orderNumber);
        return getNextOrderStatuses(placedOrder.getOrderStatus())
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
