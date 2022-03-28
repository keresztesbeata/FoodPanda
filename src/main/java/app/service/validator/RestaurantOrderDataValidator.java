package app.service.validator;

import app.dto.RestaurantOrderDto;
import app.exceptions.InvalidDataException;
import app.model.Food;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOrderDataValidator implements DataValidator<RestaurantOrderDto> {

    private static final String MISSING_USER_ERROR_MESSAGE = "Invalid data! No user specified!";
    private static final String MISSING_RESTAURANT_ERROR_MESSAGE = "Invalid data! No restaurant selected!";
    private static final String MISSING_ADDRESS_ERROR_MESSAGE = "Missing delivery address!";

    @Override
    public void validate(RestaurantOrderDto restaurantOrderDto) throws InvalidDataException {
        if(restaurantOrderDto.getCustomer() == null || restaurantOrderDto.getCustomer().isEmpty()) {
            throw new InvalidDataException(MISSING_USER_ERROR_MESSAGE);
        }
        if(restaurantOrderDto.getRestaurant() == null || restaurantOrderDto.getRestaurant().isEmpty()) {
            throw new InvalidDataException(MISSING_RESTAURANT_ERROR_MESSAGE);
        }

        if(restaurantOrderDto.getDeliveryAddress() == null || restaurantOrderDto.getDeliveryAddress().isEmpty()) {
            throw new InvalidDataException(MISSING_ADDRESS_ERROR_MESSAGE);

        }
    }
}
