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
    /**
     * Find a delivery zone by its name.
     *
     * @param name the name of the delivery zone to be added.
     * @return the existing delivery zone wrapped in an Optional or an empty Optional if no delivery zones exists with the given name
     */
    Optional<DeliveryZone> findByName(String name);

    /**
     * Find all delivery zones of a restaurant.
     *
     * @param restaurant the name of the restaurant whose delivery zones are to be fetched
     * @return a list of all delivery zones belonging to the given restaurant
     */
    @Transactional
    @Query("SELECT deliveryZone from Restaurant restaurant left join restaurant.deliveryZones deliveryZone where restaurant.name = ?1")
    List<DeliveryZone> findByRestaurant(String restaurant);
}
