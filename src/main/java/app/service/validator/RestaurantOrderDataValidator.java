package app.service.validator;

import app.dto.RestaurantOrderDto;
import app.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOrderDataValidator implements DataValidator<RestaurantOrderDto> {

    private static final String MISSING_USER_ERROR_MESSAGE = "Invalid data! No user specified!";
    private static final String MISSING_ADDRESS_ERROR_MESSAGE = "Missing delivery address!";

    @Override
    public void validate(RestaurantOrderDto restaurantOrderDto) throws InvalidDataException {
        MessageBuilder messageBuilder = new MessageBuilder();

        if (restaurantOrderDto.getCustomer() == null || restaurantOrderDto.getCustomer().isEmpty()) {
            messageBuilder.append(MISSING_USER_ERROR_MESSAGE);
        }

        if (restaurantOrderDto.getDeliveryAddress() == null || restaurantOrderDto.getDeliveryAddress().isEmpty()) {
            messageBuilder.append(MISSING_ADDRESS_ERROR_MESSAGE);
        }

        if (messageBuilder.length() > 0) {
            throw new InvalidDataException(messageBuilder.build());
        }
    }
}
