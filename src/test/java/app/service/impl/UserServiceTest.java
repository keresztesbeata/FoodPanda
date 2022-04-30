package app.service.impl;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.InvalidDataException;
import app.mapper.UserMapper;
import app.model.User;
import app.model.UserRole;
import app.repository.CartRepository;
import app.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static app.service.impl.TestUtils.*;
import static app.service.impl.TestComponentFactory.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Spy
    private UserRepository userRepository;
    @Spy
    private CartRepository cartRepository;
    @Spy
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSuccessfulUserRegistration() {
        UserDto validUserDto = createRandomUserDto(UserRole.CUSTOMER);
        String validUsername = validUserDto.getUsername();

        // initially no user with given username exists in the data source
        Mockito.when(userRepository.findByUsername(validUsername)).thenReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> userService.addUser(validUserDto));

        User validUser = convertUserDtoToEntity(validUserDto);
        // after adding the user with the valid username, it will exist in the data source, and it can be retrieved using the unique username
        Mockito.when(userRepository.findByUsername(validUsername)).thenReturn(Optional.of(validUser));

        Assertions.assertDoesNotThrow(() -> userService.getUserByUsername(validUsername));
        Assertions.assertEquals(validUsername, userService.getUserByUsername(validUsername).getUsername());
    }

    @Test(expected = DuplicateDataException.class)
    public void testFailedUserRegistrationDuplicateData() {
        User duplicateUser = createRandomUser(UserRole.CUSTOMER);
        String duplicateUsername = duplicateUser.getUsername();

        // there already exists a user in the DB with the given username
        Mockito.when(userRepository.findByUsername(duplicateUsername)).thenReturn(Optional.of(duplicateUser));

        UserDto duplicateUserDto = createRandomUserDto(UserRole.CUSTOMER);
        duplicateUserDto.setUsername(duplicateUsername);

        userService.addUser(duplicateUserDto);
    }

    @Test(expected = InvalidDataException.class)
    public void testFailedUserRegistrationInvalidData() {
        List<UserDto> invalidUsersList = new ArrayList<>();

        invalidUsersList.add(createRandomUserDto(UserRole.CUSTOMER));
        invalidUsersList.add(createRandomUserDto(UserRole.CUSTOMER));
        invalidUsersList.add(createRandomUserDto(UserRole.CUSTOMER));
        invalidUsersList.add(createRandomUserDto(UserRole.CUSTOMER));

        invalidUsersList.get(0).setUsername(null);
        invalidUsersList.get(1).setUsername("");
        invalidUsersList.get(2).setPassword(null);
        invalidUsersList.get(3).setPassword("");

        invalidUsersList.forEach(userDto -> userService.addUser(userDto));
    }

}