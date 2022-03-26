package app.service.validator;

import app.dto.PlacedOrderDto;
import app.exceptions.InvalidDataException;

public class PlacedOrderValidator implements Validator<PlacedOrderDto> {

    private static final String MISSING_USER_ERROR_MESSAGE = "Invalid data! No user specified!";
    private static final String MISSING_RESTAURANT_ERROR_MESSAGE = "Invalid data! No restaurant selected!";

    @Override
    public void validate(PlacedOrderDto placedOrderDto) throws InvalidDataException {
        if(placedOrderDto.getUser() == null || placedOrderDto.getUser().isEmpty()) {
            throw new InvalidDataException(MISSING_USER_ERROR_MESSAGE);
        }
        if(placedOrderDto.getRestaurant() == null || placedOrderDto.getRestaurant().isEmpty()) {
            throw new InvalidDataException(MISSING_RESTAURANT_ERROR_MESSAGE);
        }
    }
}
