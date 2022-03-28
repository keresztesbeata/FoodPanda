package app.repository;

import app.model.RestaurantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrder, Integer> {

    Optional<RestaurantOrder> findByOrderNumber(String orderNumber);

    @Transactional
    @Query("SELECT restaurantOrder from RestaurantOrder restaurantOrder left join restaurantOrder.customer user where user.username = ?1")
    List<RestaurantOrder> findByUser(String username);

    @Transactional
    @Query("SELECT restaurantOrder from RestaurantOrder restaurantOrder left join restaurantOrder.restaurant restaurant where restaurant.name = ?1")
    List<RestaurantOrder> findByRestaurant(String restaurantName);

    @Transactional
    @Query("SELECT restaurantOrder from RestaurantOrder restaurantOrder left join restaurantOrder.customer user where user.username = ?1 and restaurantOrder.orderStatus = ?2")
    List<RestaurantOrder> findByUserAndStatus(String username, String orderStatus);

    @Transactional
    @Query("SELECT restaurantOrder from RestaurantOrder restaurantOrder left join restaurantOrder.restaurant restaurant where restaurant.name = ?1 and restaurantOrder.orderStatus = ?2")
    List<RestaurantOrder> findByRestaurantAndStatus(String restaurantName, String orderStatus);
}
