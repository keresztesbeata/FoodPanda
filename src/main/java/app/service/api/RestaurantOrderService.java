package app.service.api;

import app.dto.RestaurantOrderDto;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.model.OrderStatus;
import app.model.User;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.Optional;

public interface RestaurantOrderService {

    /**
     * Get all the possible order statuses.
     *
     * @return the list of the names of order statuses
     */
    List<String> getAllOrderStatuses();

    /**
     * Get an order by its order number.
     *
     * @param orderNumber the unique number which identifies an order
     * @return the found order with the given order number
     * @throws EntityNotFoundException if no order with the given identifier was found
     */
    RestaurantOrderDto getOrderByOrderNumber(String orderNumber) throws EntityNotFoundException;

    /**
     * Get all orders of a given customer and with given status.
     *
     * @param user the customer whose orders are to be fetched
     * @param orderStatus the status of the searched orders
     * @return a list of orders belonging to the given customer, filtered by the given status
     */
    List<RestaurantOrderDto> getOrdersOfCustomerByStatus(User user, Optional<String> orderStatus);

    /**
     * Get all orders belonging to a restaurant and with given status.
     *
     * @param restaurantName the name of the restaurant whose orders are to be fetched
     * @param orderStatus the status of the searched orders
     * @return a list of orders belonging to the given restaurant, filtered by the given status
     */
    List<RestaurantOrderDto> getOrdersOfRestaurantByStatus(String restaurantName, Optional<String> orderStatus);

    /**
     * Add a new order for a given restaurant.
     *
     * @param restaurantOrderDto the data related to the order which is to be added
     * @throws InvalidDataException if the data related to the order is invalid,
     * ex. contains missing/null values, non-numerical values for numerical fields, etc.
     */
    void addOrder(RestaurantOrderDto restaurantOrderDto) throws InvalidDataException;

    /**
     * Update the status of an existing order.
     *
     * @param orderNumber the number which uniquely identifies the order
     * @param orderStatus the new status of the order
     * @throws IllegalStateException if the order's status cannot be updated to the new status
     * @throws EntityNotFoundException if no order was found with the given order number.
     */
    void updateOrderStatus(String orderNumber, String orderStatus) throws IllegalStateException, EntityNotFoundException;
}
