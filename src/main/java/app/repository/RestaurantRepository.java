package app.repository;


import app.model.DeliveryZone;
import app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findByName(String name);

    @Query("SELECT restaurant from Restaurant restaurant where ?1 in restaurant.deliveryZones")
    List<Restaurant> findByDeliveryZone(DeliveryZone deliveryZone);
}
