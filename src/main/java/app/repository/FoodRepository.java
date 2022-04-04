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
    Optional<Food> findByName(String name);

    @Transactional
    @Query("SELECT food from Food food left join food.restaurant restaurant where food.name = ?1 and restaurant.name = ?2")
    Optional<Food> findByNameAndRestaurant(String food, String restaurant);

    @Transactional
    @Query("SELECT food from Food food left join food.restaurant restaurant where restaurant.name = ?1")
    List<Food> findAllByRestaurant(String restaurant);

    @Transactional
    @Query("SELECT food from Food food left join food.category category left join food.restaurant restaurant where restaurant.name = ?1 and category.name = ?2")
    List<Food> findByRestaurantAndCategory(String restaurant, String category);
}
