package app.repository;

import app.model.OrderStatus;
import app.model.RestaurantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrder, Integer> {

    /**
     * Find order by order number.
     *
     * @param orderNumber the unique number associated to an order
     * @return the existing order with the given order number wrapped in an Optional or an empty Optional if no order with the given number exists
     */
    Optional<RestaurantOrder> findByOrderNumber(String orderNumber);

    /**
     * Find all orders of a customer.
     *
     * @param username the username of the customer
     * @return a list of all orders belonging to the given customer
     */
    @Transactional
    @Query("SELECT restaurantOrder from RestaurantOrder restaurantOrder left join restaurantOrder.customer user where user.username = ?1")
    List<RestaurantOrder> findByUser(String username);

    /**
     * Find all orders of a restaurant.
     *
     * @param restaurantName the name of the restaurant whose orders have to be retrieved
     * @return the list of all orders made at the given restaurant
     */
    @Transactional
    @Query("SELECT restaurantOrder from RestaurantOrder restaurantOrder left join restaurantOrder.restaurant restaurant where restaurant.name = ?1")
    List<RestaurantOrder> findByRestaurant(String restaurantName);

    /**
     * Find all orders of a customer by status.
     *
     * @param username the name of the customer
     * @param orderStatus the status of the selected orders
     * @return the list of all orders belonging to the given customer with the given status
     */
    @Transactional
    @Query("SELECT restaurantOrder from RestaurantOrder restaurantOrder left join restaurantOrder.customer user where user.username = ?1 and restaurantOrder.orderStatus = ?2")
    List<RestaurantOrder> findByUserAndStatus(String username, OrderStatus orderStatus);

    /**
     * Find all orders of a restaurant by status.
     *
     * @param restaurantName the name of the restaurant
     * @param orderStatus the status of the selected orders
     * @return the list of all orders belonging to the given restaurant with the given status
     */
    @Transactional
    @Query("SELECT restaurantOrder from RestaurantOrder restaurantOrder left join restaurantOrder.restaurant restaurant where restaurant.name = ?1 and restaurantOrder.orderStatus = ?2")
    List<RestaurantOrder> findByRestaurantAndStatus(String restaurantName, OrderStatus orderStatus);
}
