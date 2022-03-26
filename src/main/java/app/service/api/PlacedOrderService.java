package app.service.api;

import app.dto.PlacedOrderDto;
import app.exceptions.InvalidDataException;

import java.util.List;
import java.util.Optional;

public interface PlacedOrderService {

    List<PlacedOrderDto> getAllPlacedOrders();

    List<PlacedOrderDto> getPlacedOrdersOfUserByStatus(String username, Optional<String> orderStatus);

    List<PlacedOrderDto> getPlacedOrdersOfRestaurantByStatus(String restaurantName, Optional<String> orderStatus);

    void addOrder(PlacedOrderDto placedOrderDto) throws InvalidDataException;
}
