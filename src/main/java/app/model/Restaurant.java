package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @Column(nullable = false)
    private String address;

    @Column
    private Integer openingHour = 0;

    @Column
    private Integer closingHour = 0;

    @Column
    private Double deliveryFee = 0d;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "restaurant_delivery_zones",
            joinColumns = {@JoinColumn(name = "restaurant_id")},
            inverseJoinColumns = {@JoinColumn(name = "zone_id")}
    )
    private Set<DeliveryZone> deliveryZones;

    @OneToMany(mappedBy = "restaurant")
    private Set<RestaurantOrder> restaurantOrders;

    @OneToMany(mappedBy = "restaurant")
    private Set<Food> menu;

    public void addFood(Food food) {
        menu.add(food);
    }

    public void addOrder(RestaurantOrder restaurantOrder) {
        restaurantOrders.add(restaurantOrder);
    }

}
