package app.repository;

import app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * Find user by username.
     *
     * @param username the unique username which identifies a user
     * @return the existing user with the given username wrapped in an Optional or an empty Optional if no user exists with the given username
     */
    Optional<User> findByUsername(String username);
}
