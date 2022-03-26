package app.mapper;

import app.dto.CartDto;
import app.model.Cart;

import java.util.Map;
import java.util.stream.Collectors;

public class CartMapper implements Mapper<Cart, CartDto> {
    @Override
    public CartDto toDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setFoods(cart
                .getFoods()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue)));
        return cartDto;
    }

    @Override
    public Cart toEntity(CartDto cartDto) {
        return new Cart();
    }
}
