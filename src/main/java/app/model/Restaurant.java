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
}
