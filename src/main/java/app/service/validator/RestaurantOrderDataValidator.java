package app.service.validator;

import app.dto.RestaurantOrderDto;
import app.exceptions.InvalidDataException;
import app.model.Food;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOrderDataValidator implements DataValidator<RestaurantOrderDto> {

    private static final String MISSING_USER_ERROR_MESSAGE = "Invalid data! No user specified!";
    private static final String MISSING_RESTAURANT_ERROR_MESSAGE = "Invalid data! No restaurant selected!";
    private static final String MULTIPLE_RESTAURANT_ERROR_MESSAGE = "Invalid data! Cannot order from multiple restaurants at the same time!";

    @Override
    public void validate(RestaurantOrderDto restaurantOrderDto) throws InvalidDataException {
        if(restaurantOrderDto.getCustomer() == null || restaurantOrderDto.getCustomer().isEmpty()) {
            throw new InvalidDataException(MISSING_USER_ERROR_MESSAGE);
        }
        if(restaurantOrderDto.getRestaurant() == null || restaurantOrderDto.getRestaurant().isEmpty()) {
            throw new InvalidDataException(MISSING_RESTAURANT_ERROR_MESSAGE);
        }

        long nrOfDifferentRestaurants = restaurantOrderDto.getOrderedFoods()
                .keySet()
                .stream()
                .distinct()
                .count();

         if(nrOfDifferentRestaurants != 1) {
             throw new InvalidDataException(MULTIPLE_RESTAURANT_ERROR_MESSAGE);
         }
    }
}
