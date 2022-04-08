package app.service.validator;

import app.dto.FoodDto;
import app.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodDataValidator implements DataValidator<FoodDto> {
    private static final String MISSING_NAME_ERROR_MESSAGE = "The name of the food cannot be empty!";
    private static final String MISSING_CATEGORY_ERROR_MESSAGE = "The category cannot be missing!";
    private static final String MISSING_RESTAURANT_ERROR_MESSAGE = "The restaurant cannot be missing!";
    private static final String INVALID_PRICE_ERROR_MESSAGE = "The price must have a positive value!";
    private static final String INVALID_PORTION_ERROR_MESSAGE = "The portion must have a positive value!";

    @Override
    public void validate(FoodDto foodDto) throws InvalidDataException {
        MessageBuilder messageBuilder = new MessageBuilder();

        if (foodDto.getName() == null || foodDto.getName().isEmpty()) {
            messageBuilder.append(MISSING_NAME_ERROR_MESSAGE);
        }
        if(foodDto.getCategory() == null || foodDto.getCategory().isEmpty()) {
            messageBuilder.append(MISSING_CATEGORY_ERROR_MESSAGE);
        }
        if (foodDto.getRestaurant() == null || foodDto.getRestaurant().isEmpty()) {
            messageBuilder.append(MISSING_RESTAURANT_ERROR_MESSAGE);
        }
        if (foodDto.getPrice() == null || foodDto.getPrice() <= 0) {
            messageBuilder.append(INVALID_PRICE_ERROR_MESSAGE);
        }
        if (foodDto.getPortion() == null || foodDto.getPortion() <= 0) {
            messageBuilder.append(INVALID_PORTION_ERROR_MESSAGE);
        }
        if(messageBuilder.length() > 0) {
            throw new InvalidDataException(messageBuilder.build());
        }
    }
}
