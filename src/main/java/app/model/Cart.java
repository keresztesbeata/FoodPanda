package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User customer;

    @ElementCollection
    @CollectionTable(name = "foods_in_cart",
            joinColumns = {@JoinColumn(name = "cart_id",
                    referencedColumnName = "id")})
    @MapKeyColumn(name = "name")
    @Column(name = "quantity")
    private Map<Food, Integer> foods = new HashMap<>();

    @Column
    private Double totalPrice = 0d;

    public void addFoodWithQuantity(Food food, int quantity) {
        int oldQuantity = 0;
        if(foods.containsKey(food)) {
            oldQuantity = foods.get(food);
        }
        foods.put(food, quantity);
        totalPrice = ValueRounderUtil.roundValue(totalPrice + food.getPrice() * (quantity - oldQuantity));
    }

    public void deleteFood(Food food) {
        int oldQuantity = foods.get(food);
        foods.remove(food);
        totalPrice = ValueRounderUtil.roundValue(totalPrice - food.getPrice() * oldQuantity);
    }

    public void deleteAllFood() {
        foods.clear();
        totalPrice = 0d;
    }
}
