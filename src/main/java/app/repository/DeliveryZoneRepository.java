package app.repository;

import app.model.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, Integer> {
    Optional<DeliveryZone> findByName(String name);

    @Transactional
    @Query("SELECT deliveryZone from Restaurant restaurant left join restaurant.deliveryZones deliveryZone where restaurant.name = ?1")
    List<DeliveryZone> findByRestaurant(String restaurant);
}
