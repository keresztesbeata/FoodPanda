package app.service.validator;

import app.dto.FoodDto;
import app.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodDataValidator implements DataValidator<FoodDto> {
    private static final String MISSING_NAME_ERROR_MESSAGE = "The name of the food cannot be empty!";
    private static final String MISSING_RESTAURANT_ERROR_MESSAGE = "The restaurant cannot be missing!";
    private static final String INVALID_PRICE_ERROR_MESSAGE = "The price cannot have a negative value";

    @Autowired
    private NutritionFactsValidator nutritionFactsValidator;

    @Override
    public void validate(FoodDto foodDto) throws InvalidDataException {
        if (foodDto.getName() == null || foodDto.getName().isEmpty()) {
            throw new InvalidDataException(MISSING_NAME_ERROR_MESSAGE);
        }
        if (foodDto.getPrice() < 0) {
            throw new InvalidDataException(INVALID_PRICE_ERROR_MESSAGE);
        }
        if (foodDto.getRestaurant() == null || foodDto.getRestaurant().isEmpty()) {
            throw new InvalidDataException(MISSING_RESTAURANT_ERROR_MESSAGE);
        }
        foodDto.getNutritionFacts().ifPresent(nutritionFactsDto -> nutritionFactsValidator.validate(nutritionFactsDto));
    }
}
