package app.service.impl;

import app.config.UserDetailsImpl;
import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.exceptions.InvalidLoginException;
import app.mapper.UserMapper;
import app.model.Cart;
import app.model.User;
import app.model.UserRole;
import app.repository.CartRepository;
import app.repository.UserRepository;
import app.service.api.UserService;
import app.service.validator.UserDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final String ALREADY_LOGGED_IN_ERROR_MESSAGE = "You are already logged in into the application!";
    private static final String DUPLICATE_USERNAME_ERROR_MESSAGE = "Duplicate username!\nThis username is already taken!";
    private static final String INVALID_USERNAME_ERROR_MESSAGE = "Invalid username!";
    private static final String INVALID_PASSWORD_ERROR_MESSAGE = "Invalid password!";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDataValidator userDataValidator;

    @Override
    public UserDto getUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void addUser(UserDto userDto) throws InvalidDataException, DuplicateDataException {
        userDataValidator.validate(userDto);

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_USERNAME_ERROR_MESSAGE);
        }
        User user = userMapper.toEntity(userDto);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        if(user.getUserRole().equals(UserRole.CUSTOMER)) {
            // create cart for new customers
            Cart cart = new Cart();
            cart.setCustomer(savedUser);
            cartRepository.save(cart);
        }
    }

    @Override
    public UserDto authenticateUser(UserDto userDto) throws InvalidLoginException {
        System.out.println("authenticate");

        User existingUser = userRepository
                .findByUsername(userDto.getUsername())
                .orElseThrow(() -> new InvalidLoginException(INVALID_USERNAME_ERROR_MESSAGE));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(userDto.getPassword(), existingUser.getPassword())) {
            throw new InvalidLoginException(INVALID_PASSWORD_ERROR_MESSAGE);
        }

        return userMapper.toDto(existingUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(INVALID_USERNAME_ERROR_MESSAGE));

        return new UserDetailsImpl(user);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(INVALID_USERNAME_ERROR_MESSAGE));
    }
}
