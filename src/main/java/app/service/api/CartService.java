package app.service.api;

import app.dto.CartDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.model.User;

public interface CartService {

    /**
     * Get the cart of a customer.
     *
     * @param user the customer whose cart is to be retrieved
     * @return the cart which belongs to the given customer.
     * Each customer has an associated cart which is created when the customer registers to the application.
     * Every customer has exactly one cart. Restaurant admins cannot have a cart.
     */
    CartDto getCartOfCustomer(User user);

    /**
     * Remove all items from a cart.
     *
     * @param user the customer whose cart is to be emptied
     * @throws EntityNotFoundException of the customer has no associated cart
     * @return the cart's content after it has been reset
     */
    CartDto resetCart(User user) throws EntityNotFoundException;

    /**
     * Add a new food item to the cart of a customer.
     *
     * @param user the customer whose cart is updated
     * @param foodName the name of the food to be added to the cart
     * @param quantity the number of items added of the given food
     * @throws InvalidDataException if the name of the food is null or the quantity is not a positive number
     * @throws EntityNotFoundException if the customer has no associated cart or no food was found with the given name
     * @return the cart's content after the given food has been added to it
     */
    CartDto addFoodToCart(User user, String foodName, int quantity) throws InvalidDataException, EntityNotFoundException;

    /**
     * Remove a food item from the cart of a customer.
     *
     * @param user the customer whose cart is updated
     * @param foodName the name of the food to be removed from the cart
     * @throws InvalidDataException if the name of the food is null
     * @throws EntityNotFoundException if the customer has no associated cart or no food was found with the given name
     * @return the cart's content after the given food has been removed from it
     */
    CartDto removeFoodFromCart(User user, String foodName) throws InvalidDataException, EntityNotFoundException;
}
