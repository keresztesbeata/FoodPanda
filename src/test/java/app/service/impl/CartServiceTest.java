package app.service.impl;

import app.mapper.CartMapper;
import app.model.*;
import app.repository.CartRepository;
import app.repository.FoodRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static app.service.impl.TestComponentFactory.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {
    @Spy
    private CartRepository cartRepository;
    @Spy
    private FoodRepository foodRepository;
    @Spy
    private CartMapper cartMapper;
    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    public void testGetCartOfCustomer() {
        User customer = createRandomUser(UserRole.CUSTOMER);
        Cart cart = createRandomCart(customer);

        Mockito.when(cartRepository.findByCustomer(customer.getUsername()))
                .thenReturn(Optional.of(cart));

        Assertions.assertEquals(customer.getUsername(), cartService.getCartOfCustomer(customer).getCustomerName());
    }

    @Test
    public void testResetCart() {
        User customer = createRandomUser(UserRole.CUSTOMER);
        Cart cart = createRandomCart(customer);
        Restaurant restaurant = createRandomRestaurant();
        int nrFoods = 10;
        List<Food> foods = createRandomFoodsList(restaurant, nrFoods);
        for (Food food : foods) {
            cart.addFoodWithQuantity(food, 2);
        }

        Mockito.when(cartRepository.findByCustomer(customer.getUsername()))
                .thenReturn(Optional.of(cart));

        cart.deleteAllFood();
        Mockito.when(cartRepository.save(cart))
                .thenReturn(cart);

        Assertions.assertEquals(0, cartService.resetCart(customer).getFoods().size());
    }

    @Test
    public void testAddFoodToCart() {
        User customer = createRandomUser(UserRole.CUSTOMER);
        Cart cart = createRandomCart(customer);
        Restaurant restaurant = createRandomRestaurant();
        int nrFoods = 10;
        List<Food> foods = createRandomFoodsList(restaurant, nrFoods);
        for (Food food : foods) {
            cart.addFoodWithQuantity(food, 1);
        }
        Food food = createRandomFood(restaurant);
        int quantity = 2;

        Mockito.when(cartRepository.findByCustomer(customer.getUsername()))
                .thenReturn(Optional.of(cart));
        Mockito.when(foodRepository.findByName(food.getName()))
                .thenReturn(Optional.of(food));
        Mockito.when(cartRepository.save(cart))
                .thenReturn(cart);

        Assertions.assertEquals(cart.getFoods().getOrDefault(food, 0) + quantity,
                cartService.addFoodToCart(customer, food.getName(), quantity).getFoods().get(food.getName()));
    }

    @Test
    public void testRemoveFoodFromCart() {
        User customer = createRandomUser(UserRole.CUSTOMER);
        Cart cart = createRandomCart(customer);
        Restaurant restaurant = createRandomRestaurant();
        int nrFoods = 10;
        List<Food> foods = createRandomFoodsList(restaurant, nrFoods);
        for (Food food : foods) {
            cart.addFoodWithQuantity(food, 1);
        }
        Food food = foods.get(0);

        Mockito.when(cartRepository.findByCustomer(customer.getUsername()))
                .thenReturn(Optional.of(cart));
        Mockito.when(foodRepository.findByName(food.getName()))
                .thenReturn(Optional.of(food));
        Mockito.when(cartRepository.save(cart))
                .thenReturn(cart);

        Assertions.assertFalse(cartService.removeFoodFromCart(customer, food.getName()).getFoods().containsKey(food.getName()));
    }
}
