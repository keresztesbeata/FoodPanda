package app.service.validator;

import app.dto.UserDto;
import app.exceptions.InvalidDataException;

public class UserValidator implements Validator<UserDto> {
    private static final String MISSING_USERNAME_ERROR_MESSAGE = "The username cannot be missing!";
    private static final String MISSING_PASSWORD_ERROR_MESSAGE = "The password cannot be missing!";

    @Override
    public void validate(UserDto userDto) throws InvalidDataException {
        if(userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new InvalidDataException(MISSING_USERNAME_ERROR_MESSAGE);
        }
        if(userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new InvalidDataException(MISSING_PASSWORD_ERROR_MESSAGE);
        }
    }
}
