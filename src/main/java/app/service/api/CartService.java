package app.service.api;

import app.dto.CartDto;
import app.exceptions.EntityNotFoundException;

public interface CartService {

    CartDto getCartOfUser(String username) throws EntityNotFoundException;

    void resetCart(String username) throws EntityNotFoundException;

    void addFoodToCart(String username, String foodName, int quantity) throws EntityNotFoundException;

    void removeFoodFromCart(String username, String foodName) throws EntityNotFoundException;
}
