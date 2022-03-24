package app.service.api;

import app.dto.DeliveryZoneDto;
import app.dto.RestaurantDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> getAllRestaurants();

    RestaurantDto getRestaurantById(Integer id) throws EntityNotFoundException;

    RestaurantDto getRestaurantByName(String name) throws EntityNotFoundException;

    List<RestaurantDto> getRestaurantsByDeliveryZone(String deliveryZoneName);

    void addRestaurant(RestaurantDto restaurantDto) throws InvalidDataException, DuplicateDataException;
}
