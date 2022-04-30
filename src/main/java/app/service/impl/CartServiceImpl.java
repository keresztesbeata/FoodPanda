package app.service.impl;

import app.dto.CartDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.CartMapper;
import app.model.Cart;
import app.model.Food;
import app.model.User;
import app.repository.CartRepository;
import app.repository.FoodRepository;
import app.service.api.CartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CartServiceImpl implements CartService {

    private static final String CART_NOT_FOUND_ERROR_MESSAGE = "The user has no cart!";
    private static final String INEXISTENT_FOOD_ERROR_MESSAGE = "No such food exists!";
    private static final String INEXISTENT_FOOD_IN_CART_ERROR_MESSAGE = "There's no such food in the cart!";
    private static final String NEGATIVE_QUANTITY_ERROR_MESSAGE = "The new quantity of food should be positive!";

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public CartDto getCartOfCustomer(User user) {
        return cartMapper.toDto(
                cartRepository.findByCustomer(user.getUsername())
                        .orElseGet(() -> {
                            log.warn("CartServiceImpl: getCartOfCustomer: The customer "+user.getUsername()+" had no cart so we created a new one!");
                            Cart newCart = new Cart();
                            newCart.setCustomer(user);
                            return cartRepository.save(newCart);
                        })
        );
    }

    @Override
    public CartDto resetCart(User user) throws EntityNotFoundException {
        Cart cart = cartRepository.findByCustomer(user.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(CART_NOT_FOUND_ERROR_MESSAGE));
        cart.deleteAllFood();
        Cart savedCart = cartRepository.save(cart);
        log.info("CartServiceImpl: resetCart: The cart of customer " + user.getUsername() + "has been successfully reset!");

        return cartMapper.toDto(savedCart);
    }

    @Override
    public CartDto addFoodToCart(User user, String foodName, int quantity) throws EntityNotFoundException {
        Cart cart = cartRepository.findByCustomer(user.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(CART_NOT_FOUND_ERROR_MESSAGE));
        Food food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_FOOD_ERROR_MESSAGE));
        if (quantity <= 0) {
            throw new InvalidDataException(NEGATIVE_QUANTITY_ERROR_MESSAGE);
        }
        cart.addFoodWithQuantity(food, quantity);
        Cart savedCart = cartRepository.save(cart);
        log.info("CartServiceImpl: addFoodToCart: The food "+foodName + " has been successfully added to the cart of customer "+user.getUsername() + "!");

        return cartMapper.toDto(savedCart);
    }

    @Override
    public CartDto removeFoodFromCart(User user, String foodName) throws EntityNotFoundException {
        Cart cart = cartRepository.findByCustomer(user.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(CART_NOT_FOUND_ERROR_MESSAGE));
        Food food = foodRepository.findByName(foodName)
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_FOOD_ERROR_MESSAGE));
        if (!cart.getFoods().containsKey(food)) {
            throw new InvalidDataException(INEXISTENT_FOOD_IN_CART_ERROR_MESSAGE);
        }
        cart.deleteFood(food);
        Cart savedCart = cartRepository.save(cart);
        log.info("CartServiceImpl: removeFoodFromCart: The food "+foodName + " has been successfully removed from the cart of customer "+user.getUsername() + "!");

        return cartMapper.toDto(savedCart);
    }
}
