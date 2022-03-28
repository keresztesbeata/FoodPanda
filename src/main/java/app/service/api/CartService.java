package app.service.api;

import app.dto.CartDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;

public interface CartService {

    CartDto getCartOfCustomer(String username) throws EntityNotFoundException;

    void resetCart(String username) throws EntityNotFoundException;

    void addFoodToCart(String username, String foodName, int quantity) throws InvalidDataException, EntityNotFoundException;

    void removeFoodFromCart(String username, String foodName) throws InvalidDataException, EntityNotFoundException;
}
