package app.repository;

import app.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    @Query("SELECT userSession from UserSession userSession left join userSession.user user where user.username = ?1")
    Optional<UserSession> findByUsername(String username);
}
