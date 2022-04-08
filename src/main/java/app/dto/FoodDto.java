package app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
public class FoodDto {
    private String name;
    private String description;
    private String category;
    private Integer portion;
    private Double price;
    private String restaurant;
}
