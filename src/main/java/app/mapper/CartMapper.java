package app.mapper;

import app.dto.CartDto;
import app.exceptions.EntityNotFoundException;
import app.model.Cart;
import app.repository.FoodRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CartMapper implements Mapper<Cart, CartDto> {

    private static final String INEXISTENT_FOOD_ERROR_MESSAGE = "The food cannot be added to the cart! No such food!";
    private static final String INEXISTENT_USER_ERROR_MESSAGE = "The cart cannot be fetched! No such user exists!";

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CartDto toDto(Cart cart) {
        CartDto cartDto = new CartDto();

        cartDto.setCustomerName(cart.getCustomer().getUsername());
        cartDto.setFoods(cart
                .getFoods()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue)));
        cartDto.setTotalPrice(cart.getTotalPrice());

        return cartDto;
    }

    @Override
    public Cart toEntity(CartDto cartDto) {
        Cart cart = new Cart();

        cart.setCustomer(userRepository.findByUsername(cartDto.getCustomerName())
                .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_USER_ERROR_MESSAGE)));
        cart.setFoods(cartDto.getFoods()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> foodRepository.findByName(e.getKey())
                        .orElseThrow(() -> new EntityNotFoundException(INEXISTENT_FOOD_ERROR_MESSAGE)), Map.Entry::getValue)));
        cart.setTotalPrice(cartDto.getTotalPrice());

        return cart;
    }
}
