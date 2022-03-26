package app.repository;

import app.model.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder, Integer> {
    @Transactional
    @Query("SELECT placedOrder from PlacedOrder placedOrder left join placedOrder.user user where user.username = ?1")
    List<PlacedOrder> findPlacedOrderByUser(String username);

    @Transactional
    @Query("SELECT placedOrder from PlacedOrder placedOrder left join placedOrder.restaurant restaurant where restaurant.name = ?1")
    List<PlacedOrder> findPlacedOrderByRestaurant(String restaurantName);
    @Transactional
    @Query("SELECT placedOrder from PlacedOrder placedOrder left join placedOrder.user user where user.username = ?1 and placedOrder.orderStatus = ?2")
    List<PlacedOrder> findPlacedOrderByUserAndStatus(String username, String orderStatus);

    @Transactional
    @Query("SELECT placedOrder from PlacedOrder placedOrder left join placedOrder.restaurant restaurant where restaurant.name = ?1 and placedOrder.orderStatus = ?2")
    List<PlacedOrder> findPlacedOrderByRestaurantAndStatus(String restaurantName, String orderStatus);
}
