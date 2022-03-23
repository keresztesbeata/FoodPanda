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
public class DeliveryZone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private Double deliveryFee;

    @Column
    @ManyToMany(mappedBy = "restaurant_id")
    private Set<Restaurant> restaurants = new HashSet<>();
}
