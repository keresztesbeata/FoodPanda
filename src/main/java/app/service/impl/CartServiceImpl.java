package app.service.impl;

import app.dto.CartDto;
import app.exceptions.EntityNotFoundException;
import app.mapper.CartMapper;
import app.model.Cart;
import app.model.Food;
import app.repository.CartRepository;
import app.repository.FoodRepository;
import app.repository.UserRepository;
import app.service.api.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CartServiceImpl implements CartService {

    private static final String INEXISTENT_CART_ERROR_MESSAGE = "The food cannot be added to the cart! The user has no cart!";
    private static final String INEXISTENT_FOOD_ERROR_MESSAGE = "The food cannot be added to the cart! No such food!";
    private static final String INEXISTENT_USER_ERROR_MESSAGE = "The cart cannot be fetched! No such user exists!";

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    private final CartMapper cartMapper = new CartMapper();

    @Override
    public CartDto getCartOfUser(String username) throws EntityNotFoundException {
        return cartMapper.toDto(
                cartRepository.findByUserNotCompleted(username).orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setFoods(new HashMap<>());
                    newCart.setUser(userRepository.findByUsername(username)
                            .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_USER_ERROR_MESSAGE)));
                    return cartRepository.save(newCart);
                })
        );
    }

    @Override
    public void resetCart(String username) throws EntityNotFoundException {
        Cart cart = cartRepository.findByUserNotCompleted(username)
                        .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_CART_ERROR_MESSAGE));
        cart.deleteAllFood();
        cartRepository.save(cart);
    }

    @Override
    public void addFoodToCart(String username, String foodName, int quantity) throws EntityNotFoundException {
        Cart cart = cartRepository.findByUserNotCompleted(username)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_CART_ERROR_MESSAGE));
        Food food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_FOOD_ERROR_MESSAGE));
        cart.addFoodWithQuantity(food, quantity);
        cartRepository.save(cart);
    }

    @Override
    public void removeFoodFromCart(String username, String foodName) throws EntityNotFoundException {
        Cart cart = cartRepository.findByUserNotCompleted(username)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_CART_ERROR_MESSAGE));
        Food food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_FOOD_ERROR_MESSAGE));
        cart.deleteFood(food);

        cartRepository.save(cart);
    }
}
