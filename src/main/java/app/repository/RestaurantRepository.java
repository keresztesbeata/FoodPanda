package app.repository;


import app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Optional<Restaurant> findByName(String name);

    @Transactional
    @Query("SELECT restaurant from Restaurant restaurant left join restaurant.deliveryZones deliveryZone where deliveryZone.name = ?1")
    List<Restaurant> findByDeliveryZone(String deliveryZone);
}
