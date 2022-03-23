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
    private Integer id;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private Integer number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @OneToOne(mappedBy = "address")
    private Restaurant restaurant;

    @OneToOne(mappedBy = "address")
    private User user;
}
