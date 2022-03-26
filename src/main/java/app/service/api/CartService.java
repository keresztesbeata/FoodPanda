package app.service.api;

import app.dto.FoodDto;
import app.dto.UserDto;
import app.exceptions.EntityNotFoundException;

public interface CartService {

    void addFoodToCart(UserDto userDto, FoodDto foodDto, int quantity) throws EntityNotFoundException;

    void removeFoodFromCart(UserDto userDto, FoodDto foodDto, int quantity) throws EntityNotFoundException;
}
