package app.repository;

import app.model.Cart;
import app.model.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Transactional
    @Query("SELECT cart from Cart cart left join cart.user user where user.username = ?1")
    List<Cart> findByUser(String username);

    Optional<Cart> findByPlacedOrder(PlacedOrder placedOrder);

    @Transactional
    @Query("SELECT cart from Cart cart left join cart.user user where user.username = ?1 and cart.completed = false")
    Optional<Cart> findByUserNotCompleted(String username);
}
