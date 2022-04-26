package app.service.api;

import app.dto.UserDto;
import app.exceptions.*;
import app.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto getUserByUsername(String username) throws EntityNotFoundException;

    void addUser(UserDto userDto) throws InvalidDataException, DuplicateDataException;

    void logout(String username) throws InvalidOperationException;
}
