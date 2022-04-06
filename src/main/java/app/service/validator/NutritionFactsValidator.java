package app.service.validator;

import app.dto.NutritionFactsDto;
import app.exceptions.InvalidDataException;
import org.springframework.stereotype.Component;

@Component
public class NutritionFactsValidator implements DataValidator<NutritionFactsDto> {
    private static final String INVALID_NUTRITION_VALUE_ERROR_MESSAGE = "Nutrition value cannot be negative!";

    @Override
    public void validate(NutritionFactsDto nutritionFactsDto) throws InvalidDataException {
        if (nutritionFactsDto.getFat() == null || nutritionFactsDto.getFat() < 0 ||
                nutritionFactsDto.getSodium() == null || nutritionFactsDto.getSodium() < 0 ||
                nutritionFactsDto.getCholesterol() == null || nutritionFactsDto.getCholesterol() < 0 ||
                nutritionFactsDto.getProtein() == null || nutritionFactsDto.getProtein() < 0 ||
                nutritionFactsDto.getCarbohydrate() == null || nutritionFactsDto.getCarbohydrate() < 0) {
            throw new InvalidDataException(INVALID_NUTRITION_VALUE_ERROR_MESSAGE);
        }
    }
}
