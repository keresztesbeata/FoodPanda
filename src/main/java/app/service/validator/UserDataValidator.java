package app.service.validator;

import app.dto.UserDto;
import app.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDataValidator implements DataValidator<UserDto> {
    private static final String MISSING_USERNAME_ERROR_MESSAGE = "The username cannot be missing!";
    private static final String MISSING_PASSWORD_ERROR_MESSAGE = "The password cannot be missing!";

    @Override
    public void validate(UserDto userDto) throws InvalidDataException {
        MessageBuilder messageBuilder = new MessageBuilder();

        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            messageBuilder.append(MISSING_USERNAME_ERROR_MESSAGE);
        }

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            messageBuilder.append(MISSING_PASSWORD_ERROR_MESSAGE);
        }

        if (messageBuilder.length() > 0) {
            throw new InvalidDataException(messageBuilder.build());
        }
    }
}
