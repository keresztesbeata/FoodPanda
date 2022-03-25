package app.service.api;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidLoginException;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Integer id)  throws EntityNotFoundException;

    UserDto getUserByUsername(String username)  throws EntityNotFoundException;

    void addUser(UserDto userDto) throws InvalidDataException, DuplicateDataException;

    UserDto authenticateUser(UserDto userDto) throws InvalidLoginException;
}
