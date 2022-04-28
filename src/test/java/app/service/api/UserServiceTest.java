package app.service.api;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.InvalidDataException;
import app.mapper.UserMapper;
import app.model.User;
import app.model.UserRole;
import app.repository.CartRepository;
import app.repository.UserRepository;
import app.service.impl.UserServiceImpl;
import app.service.validator.UserDataValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

// todo : add pre & post condition to service classes => Design By Contract

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Spy
    private UserRepository userRepository;
    @Spy
    private CartRepository cartRepository;
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
        // after adding the user with the valid username, it will exist in the data source and it can be retrieved using the unique username
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

    private static User createRandomUser(UserRole userRole) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setId(50);
        user.setUsername(generateRandomName(30));
        user.setPassword(passwordEncoder.encode(generateRandomName(30)));
        user.setUserRole(userRole);

        return user;
    }

    private static User convertUserDtoToEntity(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserMapper userMapper = new UserMapper();

        User user = userMapper.toEntity(userDto);
        user.setId((new Random()).nextInt());
        user.setPassword(passwordEncoder.encode(generateRandomName(30)));

        return user;
    }

    private static UserDto createRandomUserDto(UserRole userRole) {
        UserDto userDto = new UserDto();
        userDto.setUsername(generateRandomName(30));
        userDto.setPassword(generateRandomName(30));
        userDto.setUserRole(userRole.name());

        return userDto;
    }

    private static String generateRandomName(int length) {
        return (new Random(0))
                .ints(length)
                .asLongStream()
                .mapToObj(value -> String.valueOf(Character.charCount((int) (value % 255))))
                .collect(Collectors.joining());
    }
}