package app.service.api;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidLoginException;
import app.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    User getCurrentUser();

    UserDto getUserByUsername(String username)  throws EntityNotFoundException;

    void addUser(UserDto userDto) throws InvalidDataException, DuplicateDataException;

    UserDto authenticateUser(UserDto userDto) throws InvalidLoginException;
}
