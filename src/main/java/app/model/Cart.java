package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "cart")
    private PlacedOrder placedOrder;

    @ElementCollection
    @CollectionTable(name = "cart_foods_mapping",
            joinColumns = {@JoinColumn(name = "cart_id",
                    referencedColumnName = "id")})
    @MapKeyColumn(name = "name")
    @Column(name = "quantity")
    private Map<Food, Integer> foods;

    @Column
    private Double totalPrice = 0d;

    @Column
    private Boolean completed = false;

    public void addFoodWithQuantity(Food food, int quantity) {
        int oldQuantity = 0;
        if(foods.containsKey(food)) {
            oldQuantity = foods.get(food);

            if (quantity <= 0) {
                foods.remove(food);
            } else {
                foods.replace(food, quantity);
            }
        }else {
            foods.put(food, quantity);
        }
        totalPrice  = totalPrice + food.getPrice() * (quantity - oldQuantity);
    }

    public void deleteFood(Food food) {
        int oldQuantity = foods.get(food);
        foods.remove(food);
        totalPrice  = totalPrice - food.getPrice() * oldQuantity;
    }

    public void deleteAllFood() {
        foods.clear();
        totalPrice = 0d;
    }
}
