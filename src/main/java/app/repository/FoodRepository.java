package app.repository;

import app.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    /**
     * Find food by its name.
     *
     * @param name the name of the searched food
     * @return the existing food wrapped in an Optional or an empty Optional if no food with the given name exists
     */
    Optional<Food> findByName(String name);

    /**
     * Find food by its name and its restaurant.
     *
     * @param food the name of the searched food
     * @param restaurant the name of the restaurant which contains the given food
     * @return the existing food wrapped in an Optional or an empty Optional if no food with the given name and belonging to the given restaurant exists
     */
    @Transactional
    @Query("SELECT food from Food food left join food.restaurant restaurant where food.name = ?1 and restaurant.name = ?2")
    Optional<Food> findByNameAndRestaurant(String food, String restaurant);

    /**
     * Find all foods of a given restaurant.
     *
     * @param restaurant the name of the restaurant whose foods are searched
     * @return a list of all foods belonging to the given restaurant
     */
    @Transactional
    @Query("SELECT food from Food food left join food.restaurant restaurant where restaurant.name = ?1")
    List<Food> findAllByRestaurant(String restaurant);

    /**
     * Find all foods of a given restaurant and of a given category.
     *
     * @param restaurant the name of the restaurant whose foods are searched
     * @param category the name of the category to which the foods should belong
     * @return a list of all foods belonging to the given restaurant and the given category
     */
    @Transactional
    @Query("SELECT food from Food food left join food.category category left join food.restaurant restaurant where restaurant.name = ?1 and category.name = ?2")
    List<Food> findByRestaurantAndCategory(String restaurant, String category);
}
