package app.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {
    private String name;
    private String description;
    private String category;
    private Integer portion;
    private Double price;
    private String restaurant;
}
