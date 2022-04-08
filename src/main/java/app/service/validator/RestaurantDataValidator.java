package app.service.validator;

import app.dto.RestaurantDto;
import app.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantDataValidator implements DataValidator<RestaurantDto> {
    private static final String MISSING_NAME_ERROR_MESSAGE = "The name of the restaurant cannot be empty!";
    private static final String MISSING_ADDRESS_ERROR_MESSAGE = "The address of the restaurant cannot be missing!";
    private static final String MISSING_ADMIN_ERROR_MESSAGE = "The admin of the restaurant cannot be missing!";

    @Autowired
    private MessageBuilder messageBuilder;

    @Override
    public void validate(RestaurantDto restaurantDto) throws InvalidDataException {
        if (restaurantDto.getName() == null || restaurantDto.getName().isEmpty()) {
            messageBuilder.append(MISSING_NAME_ERROR_MESSAGE);
        }

        if (restaurantDto.getAddress() == null || restaurantDto.getAddress().isEmpty()) {
            messageBuilder.append(MISSING_ADDRESS_ERROR_MESSAGE);
        }

        if (restaurantDto.getAdmin() == null || restaurantDto.getAdmin().isEmpty()) {
            messageBuilder.append(MISSING_ADMIN_ERROR_MESSAGE);
        }
        if (messageBuilder.length() > 0) {
            throw new InvalidDataException(messageBuilder.build());
        }
    }
}
