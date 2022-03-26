package app.service.impl;

import app.dto.PlacedOrderDto;
import app.exceptions.InvalidDataException;
import app.mapper.PlacedOrderMapper;
import app.model.*;
import app.repository.*;
import app.service.api.PlacedOrderService;
import app.service.validator.PlacedOrderValidator;
import app.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlacedOrderServiceImpl implements PlacedOrderService {

    private static final String INVALID_USER_ERROR_MESSAGE = "The order cannot be created! Invalid user!";
    private static final String INVALID_RESTAURANT_ERROR_MESSAGE = "The order cannot be created! Invalid restaurant!";
    private static final String INEXISTENT_CART_ERROR_MESSAGE = "The order cannot be created! The user has no cart!";
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
    }


}
