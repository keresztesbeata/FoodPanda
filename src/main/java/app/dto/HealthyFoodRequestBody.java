package app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
public class HealthyFoodRequestBody {
    private BasicFoodDto basicFoodDto;
    private NutritionFactsDto nutritionFacts;
}
