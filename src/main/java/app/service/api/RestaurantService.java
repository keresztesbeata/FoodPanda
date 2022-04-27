package app.service.api;

import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidOperationException;
import app.model.User;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> getAllRestaurants();

    RestaurantDto getRestaurantOfAdmin(User admin) throws EntityNotFoundException;

    RestaurantDto getRestaurantByName(String name) throws EntityNotFoundException;

    List<RestaurantDto> getRestaurantsByDeliveryZone(String deliveryZoneName);

    void addRestaurant(RestaurantDto restaurantDto) throws InvalidDataException, DuplicateDataException, InvalidOperationException;
}
