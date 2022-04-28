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

    /**
     * Find a restaurant by its name.
     *
     * @param name the name of the searched restaurant
     * @return the existing restaurant with the given name wrapped in an Optional or an empty Optional if no restaurant with the given name exists
     */
    Optional<Restaurant> findByName(String name);

    /**
     * Find the restaurant of an admin.
     *
     * @param admin the username of the admin
     * @return the restaurant associated to the given user wrapped in an Optional or an empty Optional if the user has no associated restaurants
     */
    @Transactional
    @Query("SELECT restaurant from Restaurant restaurant left join restaurant.admin user where user.username = ?1")
    Optional<Restaurant> findByAdmin(String admin);

    /**
     * Find all restaurants by a given delivery zone.
     *
     * @param deliveryZone the name of the delivery zone after which the restaurants are selected
     * @return the list of restaurants which contain the given delivery zone
     */
    @Transactional
    @Query("SELECT restaurant from Restaurant restaurant left join restaurant.deliveryZones deliveryZone where deliveryZone.name = ?1")
    List<Restaurant> findByDeliveryZone(String deliveryZone);
}
