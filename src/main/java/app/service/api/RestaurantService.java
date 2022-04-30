package app.service.api;

import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidOperationException;
import app.model.Restaurant;
import app.model.User;

import java.util.List;

public interface RestaurantService {
    /**
     * Get the list of all the restaurants.
     *
     * @return a list of all existing restaurants.
     */
    List<RestaurantDto> getAllRestaurants();

    /**
     * Get the restaurant of a given user.
     *
     * @param admin the owner of a restaurant
     * @return the restaurant belonging to the given admin
     * @throws EntityNotFoundException if the admin has no restaurant
     */
    RestaurantDto getRestaurantOfAdmin(User admin) throws EntityNotFoundException;

    /**
     * Get a restaurant by its name.
     *
     * @param name the name of the restaurant
     * @return the restaurant with the given name
     * @throws EntityNotFoundException if no restaurant exists with the given name
     */
    RestaurantDto getRestaurantByName(String name) throws EntityNotFoundException;

    /**
     * Get the list of restaurants by delivery zone.
     *
     * @param deliveryZoneName the name of the delivery zone to be searched after
     * @return the list of restaurants which deliver to the given delivery zone
     */
    List<RestaurantDto> getRestaurantsByDeliveryZone(String deliveryZoneName);

    /**
     * Add a new restaurant.
     *
     * @param restaurantDto the data of the restaurant to be added
     * @throws InvalidDataException if the data related to the restaurant is invalid
     * @throws DuplicateDataException if the name of the restaurant is already taken
     * @throws InvalidOperationException if the admin already has a restaurant
     */
    void addRestaurant(RestaurantDto restaurantDto) throws InvalidDataException, DuplicateDataException, InvalidOperationException;

    /**
     * Export the menu of the restaurant in PDF format.
     *
     * @param restaurantName the name of the restaurant
     * @throws EntityNotFoundException if no restaurant with the given name was found
     */
    void exportMenu(String restaurantName) throws EntityNotFoundException;
}
