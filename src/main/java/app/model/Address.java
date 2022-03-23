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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "restaurant_id")
    private Integer id;
    @Column
    private String city;
    @Column
    private String street;
    @OneToOne
    @MapsId
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
