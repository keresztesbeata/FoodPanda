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
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private Address location;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "restaurant_delivery_zones",
            joinColumns = {@JoinColumn(name = "restaurant_id")},
            inverseJoinColumns = {@JoinColumn(name = "delivery_zone_id")}
    )
    private Set<DeliveryZone> deliveryZones = new HashSet<>();
}
