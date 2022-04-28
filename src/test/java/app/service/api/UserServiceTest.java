package app.service.api;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.InvalidDataException;
import app.mapper.UserMapper;
import app.model.User;
import app.repository.CartRepository;
import app.repository.UserRepository;
import app.service.validator.UserDataValidator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
//@ContextConfiguration(classes = DBDataJpaConfig.class, loader = AnnotationConfigContextLoader.class)
//@Transactional
//@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserDataValidator userDataValidator;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSuccessfulUserRegistration() {
        User user = new User();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        String username = "user1";
        UserDto validUser = new UserDto(username, "password1", "CUSTOMER");
        userService.addUser(validUser);
        // Assertions.assertDoesNotThrow(() -> userService.getUserByUsername(username));
        System.out.println(userRepository.findByUsername("user1"));
//        Assertions.assertEquals(username, userService.getUserByUsername(username).getUsername());

//        Mockito.when(userRepository.findByUsername(username))
//                .thenReturn(Optional.of(expected));
        UserDto actual = userService.getUserByUsername("user1");

        System.out.println(actual.toString());
//        ReflectionAssert.assertReflectionEquals(expected,actual);
    }

    @Test(expected = DuplicateDataException.class)
    public void testFailedUserRegistrationDuplicateData() {
        String username = "user1";
        UserDto validUser = new UserDto(username, "password1", "CUSTOMER");
        userService.addUser(validUser);

        UserDto duplicateUser = new UserDto(username, "password2", "CUSTOMER");
        Assertions.assertThrows(DuplicateDataException.class, () -> userService.addUser(duplicateUser));
    }

    @Test(expected = InvalidDataException.class)
    public void testFailedUserRegistrationInvalidData() {
        Arrays.asList(new UserDto(null, "password2", "CUSTOMER"),
                        new UserDto("", "password2", "CUSTOMER"),
                        new UserDto("user2", null, "CUSTOMER"),
                        new UserDto("user2", "", "CUSTOMER"))
                .forEach(userDto -> {
                    Assertions.assertThrows(InvalidDataException.class, () -> userService.addUser(userDto));
                });
    }

}