package app.repository;

import app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Find a food category by its name.
     *
     * @param name the name of the category
     * @return the existing food category wrapped in an Optional or an empty Optional if no food category exists with the given name
     */
    Optional<Category> findByName(String name);
}
