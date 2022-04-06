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
@Table(name="nutrition_facts")
public class NutritionFacts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(mappedBy = "food")
    private Food food;

    @Column
    private Integer protein;

    @Column
    private Integer sodium;

    @Column
    private Integer fat;

    @Column
    private Integer carbohydrate;

    @Column
    private Integer cholesterol;
}
