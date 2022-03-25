package app.service.validator;

import app.dto.RestaurantDto;
import app.exceptions.InvalidDataException;

public class RestaurantValidator implements Validator<RestaurantDto> {
    private static final String MISSING_NAME_ERROR_MESSAGE = "The name of the restaurant cannot be empty!";
    private static final String MISSING_ADDRESS_ERROR_MESSAGE = "The address of the restaurant cannot be missing!";

    @Override
    public void validate(RestaurantDto restaurantDto) throws InvalidDataException {
        if (restaurantDto.getName() == null || restaurantDto.getName().isEmpty()) {
            throw new InvalidDataException(MISSING_NAME_ERROR_MESSAGE);
        }

        if(restaurantDto.getAddress() == null || restaurantDto.getAddress().isEmpty()) {
            throw new InvalidDataException(MISSING_ADDRESS_ERROR_MESSAGE);
        }
    }
}
