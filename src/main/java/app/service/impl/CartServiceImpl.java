package app.service.impl;

import app.dto.CartDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.CartMapper;
import app.model.Cart;
import app.model.Food;
import app.repository.CartRepository;
import app.repository.FoodRepository;
import app.repository.UserRepository;
import app.service.api.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private static final String CART_NOT_FOUND_ERROR_MESSAGE = "The user has no cart!";
    private static final String INEXISTENT_FOOD_ERROR_MESSAGE = "No such food exists!";
    private static final String INEXISTENT_USER_ERROR_MESSAGE = "No such user exists!";
    private static final String INEXISTENT_FOOD_IN_CART_ERROR_MESSAGE = "There's no such food in the cart!";
    private static final String NEGATIVE_QUANTITY_ERROR_MESSAGE = "The new quantity of food should be positive!";

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public CartDto getCartOfCustomer(String username) throws EntityNotFoundException {
        return cartMapper.toDto(
                cartRepository.findByCustomer(username)
                        .orElseGet(() -> {
                            Cart newCart = new Cart();
                            newCart.setCustomer(userRepository.findByUsername(username)
                                    .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_USER_ERROR_MESSAGE)));
                            return cartRepository.save(newCart);
                        })
        );
    }

    @Override
    public void resetCart(String username) throws EntityNotFoundException {
        Cart cart = cartRepository.findByCustomer(username)
                .orElseThrow(() -> new EntityNotFoundException(CART_NOT_FOUND_ERROR_MESSAGE));
        cart.deleteAllFood();
        cartRepository.save(cart);
    }

    @Override
    public void addFoodToCart(String username, String foodName, int quantity) throws EntityNotFoundException {
        Cart cart = cartRepository.findByCustomer(username)
                .orElseThrow(() -> new EntityNotFoundException(CART_NOT_FOUND_ERROR_MESSAGE));
        Food food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_FOOD_ERROR_MESSAGE));
        if (quantity <= 0) {
            throw new InvalidDataException(NEGATIVE_QUANTITY_ERROR_MESSAGE);
        }
        cart.addFoodWithQuantity(food, quantity);
        cartRepository.save(cart);
    }

    @Override
    public void removeFoodFromCart(String username, String foodName) throws EntityNotFoundException {
        Cart cart = cartRepository.findByCustomer(username)
                .orElseThrow(() -> new EntityNotFoundException(CART_NOT_FOUND_ERROR_MESSAGE));
        Food food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_FOOD_ERROR_MESSAGE));
        if (!cart.getFoods().containsKey(food)) {
            throw new InvalidDataException(INEXISTENT_FOOD_IN_CART_ERROR_MESSAGE);
        }
        cart.deleteFood(food);

        cartRepository.save(cart);
    }
}
