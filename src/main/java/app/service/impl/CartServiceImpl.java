package app.service.impl;

import app.dto.FoodDto;
import app.dto.UserDto;
import app.exceptions.EntityNotFoundException;
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

    private static final String INEXISTENT_CART_ERROR_MESSAGE = "The food cannot be added to the cart! The user has no cart!";
    private static final String INEXISTENT_FOOD_ERROR_MESSAGE = "The food cannot be added to the cart! No such food!";

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public void addFoodToCart(UserDto userDto, FoodDto foodDto, int quantity) {
        Cart cart = cartRepository.findByUserNotCompleted(userDto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_CART_ERROR_MESSAGE));
        Food food = foodRepository.findByName(foodDto.getName())
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_FOOD_ERROR_MESSAGE));
        cart.addFoodWithQuantity(food, quantity);
        // todo update existing entity
        cartRepository.save(cart);
    }

    @Override
    public void removeFoodFromCart(UserDto userDto, FoodDto foodDto, int quantity) {
        Cart cart = cartRepository.findByUserNotCompleted(userDto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_CART_ERROR_MESSAGE));
        Food food = foodRepository.findByName(foodDto.getName())
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_FOOD_ERROR_MESSAGE));
        cart.deleteFoodWithQuantity(food, quantity);
        // todo update existing entity
        cartRepository.save(cart);
    }
}
