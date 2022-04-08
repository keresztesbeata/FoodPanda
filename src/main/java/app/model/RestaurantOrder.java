package app.model;

import app.model.order_states.AbstractOrderState;
import app.model.order_states.AcceptedState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="restaurant_order")
public class RestaurantOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique=true,length=6)
    private String orderNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private LocalDate dateCreated;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ElementCollection
    @CollectionTable(name = "ordered_foods",
            joinColumns = {@JoinColumn(name = "order_id",
                    referencedColumnName = "id")})
    @MapKeyColumn(name = "name")
    @Column(name = "quantity")
    private Map<Food, Integer> orderedFoods = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(nullable = false, length = 100)
    private String deliveryAddress;

    @Column(length = 200)
    private String remark;

    @Column
    private Boolean withCutlery = false;

    @Column
    private Double totalPrice = 0d;

    public void computeTotalPrice() {
        totalPrice = computeFoodsPrice() + restaurant.getDeliveryFee();
    }

    private double computeFoodsPrice() {
        return orderedFoods.entrySet()
                .stream()
                .map(entry -> entry.getKey().getPrice() * entry.getValue())
                .reduce(Double::sum)
                .map(ValueRounderUtil::roundValue)
                .orElse(0d);
    }

    public void addOrderedFoods(Map<Food, Integer> orderedFoods) {
        this.orderedFoods.putAll(orderedFoods);
    }
}
