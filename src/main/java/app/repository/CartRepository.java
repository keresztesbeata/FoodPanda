package app.repository;

import app.model.Cart;
import app.model.RestaurantOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    /**
     * Find the cart of a customer.
     *
     * @param customerName the name of the customer whose cart is to be retrieved
     * @return the existing cart wrapped in an Optional or an empty optional if the customer has no associated cart
     */
    @Transactional
    @Query("SELECT cart from Cart cart left join cart.customer user where user.username = ?1")
    Optional<Cart> findByCustomer(String customerName);
}
