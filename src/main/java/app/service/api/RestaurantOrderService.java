package app.service.api;

import app.dto.RestaurantOrderDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import java.util.List;
import java.util.Optional;

public interface RestaurantOrderService {

    List<String> getAllOrderStatuses();

    RestaurantOrderDto getOrderByOrderNumber(String orderNumber) throws EntityNotFoundException;

    List<RestaurantOrderDto> getOrdersOfCustomerByStatus(String customer, Optional<String> orderStatus);

    List<RestaurantOrderDto> getOrdersOfRestaurantByStatus(String restaurantName, Optional<String> orderStatus);

    void addOrder(RestaurantOrderDto restaurantOrderDto) throws InvalidDataException;

    void acceptOrder(String orderNumber) throws EntityNotFoundException;

    void declineOrder(String orderNumber) throws EntityNotFoundException;

    void setOrderDelivered(String orderNumber) throws EntityNotFoundException;

    void setOrderInDelivery(String orderNumber) throws EntityNotFoundException;

    List<String> getAvailableStatusForOrder(String orderNumber) throws EntityNotFoundException;
}
