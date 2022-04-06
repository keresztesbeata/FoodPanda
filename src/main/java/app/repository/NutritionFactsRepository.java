package app.repository;

import app.model.NutritionFacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface NutritionFactsRepository extends JpaRepository<NutritionFacts, Integer> {
//    @Transactional
//    @Query("SELECT nutritionFacts from NutritionFacts nutritionFacts left join nutritionFacts.food food where food.name = ?1")
//    Optional<NutritionFacts> findByFood(String foodName);
}
