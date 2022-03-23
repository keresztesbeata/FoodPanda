package app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Restaurant restaurant;

    @OneToOne(mappedBy = "orders", fetch = FetchType.EAGER)
    private Cart cart;
}
