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
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(mappedBy = "cart")
    private User user;

    @OneToOne(mappedBy = "cart")
    private Order order;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "food_cart",
            joinColumns = {@JoinColumn(name = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "food_id")}
    )
    private Set<Food> foods = new HashSet<>();
}
