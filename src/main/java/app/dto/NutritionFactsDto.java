package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NutritionFactsDto {
    private Integer protein;
    private Integer sodium;
    private Integer fat;
    private Integer carbohydrate;
    private Integer cholesterol;
}
