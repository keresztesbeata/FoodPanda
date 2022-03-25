package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private User admin;

    @Column(nullable = false)
    private String address;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "restaurant_delivery_zones",
            joinColumns = {@JoinColumn(name = "restaurant_id")},
            inverseJoinColumns = {@JoinColumn(name = "zone_id")}
    )
    private List<DeliveryZone> deliveryZones;

    @OneToMany(mappedBy = "restaurant")
    private List<PlacedOrder> placedOrders;

    @OneToMany(mappedBy = "restaurant")
    private List<Food> menu;

    public void addFood(Food food) {
        menu.add(food);
    }

    public void deleteFood(Food food) {
        menu.remove(food);
    }

    public void addPlacedOrder(PlacedOrder placedOrder) {
        placedOrders.add(placedOrder);
    }

    public void deletePlacedOrder(PlacedOrder placedOrder) {
        placedOrders.remove(placedOrder);
    }

}
