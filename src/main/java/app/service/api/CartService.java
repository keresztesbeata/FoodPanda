package app.service.api;

import app.dto.CartDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.model.User;

public interface CartService {

    CartDto getCartOfCustomer(User user);

    void resetCart(User user) throws EntityNotFoundException;

    void addFoodToCart(User user, String foodName, int quantity) throws InvalidDataException, EntityNotFoundException;

    void removeFoodFromCart(User user, String foodName) throws InvalidDataException, EntityNotFoundException;
}
