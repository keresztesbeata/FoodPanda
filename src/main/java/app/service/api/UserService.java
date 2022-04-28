package app.service.api;

import app.dto.UserDto;
import app.exceptions.*;
import app.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Retrieve a user by its username.
     *
     * @param username the name which uniquely identifies the user
     * @return the existing user's information
     * @throws EntityNotFoundException when no user is found with the given username
     */
    UserDto getUserByUsername(String username) throws EntityNotFoundException;

    /**
     * Add a new user.
     *
     * @param userDto contains all the relevant user related information.
     * @throws InvalidDataException when either the username or the password is missing or invalid
     * @throws DuplicateDataException when the username is already taken
     * @pre username != null && password != null
     */
    void addUser(UserDto userDto) throws InvalidDataException, DuplicateDataException;
}
