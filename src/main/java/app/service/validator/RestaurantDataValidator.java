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
    private static final String INVALID_DELIVERY_FEE_ERROR_MESSAGE = "The delivery fee cannot have a negative value!";
    private static final String INVALID_OPENING_HOUR_ERROR_MESSAGE = "The opening-hour must be in the range 0-23!";
    private static final String INVALID_CLOSING_HOUR_ERROR_MESSAGE = "The opening-hour must be in the range 0-23!";

    @Override
    public void validate(RestaurantDto restaurantDto) throws InvalidDataException {
        MessageBuilder messageBuilder = new MessageBuilder();

        if (restaurantDto.getName() == null || restaurantDto.getName().isEmpty()) {
            messageBuilder.append(MISSING_NAME_ERROR_MESSAGE);
        }

        if (restaurantDto.getAddress() == null || restaurantDto.getAddress().isEmpty()) {
            messageBuilder.append(MISSING_ADDRESS_ERROR_MESSAGE);
        }

        if (restaurantDto.getAdmin() == null || restaurantDto.getAdmin().isEmpty()) {
            messageBuilder.append(MISSING_ADMIN_ERROR_MESSAGE);
        }

        if (restaurantDto.getOpeningHour() == null || restaurantDto.getOpeningHour() < 0 || restaurantDto.getOpeningHour() > 23) {
            messageBuilder.append(INVALID_OPENING_HOUR_ERROR_MESSAGE);
        }

        if (restaurantDto.getClosingHour() == null || restaurantDto.getClosingHour() < 0 || restaurantDto.getClosingHour() > 23) {
            messageBuilder.append(INVALID_CLOSING_HOUR_ERROR_MESSAGE);
        }

        if (restaurantDto.getDeliveryFee() == null || restaurantDto.getDeliveryFee() < 0) {
            messageBuilder.append(INVALID_DELIVERY_FEE_ERROR_MESSAGE);
        }

        if (messageBuilder.length() > 0) {
            throw new InvalidDataException(messageBuilder.build());
        }
    }
}
