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
import app.service.validator.UserValidator;
import app.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final String DUPLICATE_USERNAME_ERROR_MESSAGE = "Duplicate username!\nThis username is already taken!";
    private static final String INVALID_USERNAME_ERROR_MESSAGE = "Invalid username!";
    private static final String INVALID_PASSWORD_ERROR_MESSAGE = "Invalid password!";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    private final UserMapper userMapper = new UserMapper();
    private final Validator<UserDto> userValidator = new UserValidator();

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Integer id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserDto getUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void addUser(UserDto userDto) throws InvalidDataException, DuplicateDataException {
        userValidator.validate(userDto);

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_USERNAME_ERROR_MESSAGE);
        }

        User user = userMapper.toEntity(userDto);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(savedUser);
        cart.setFoods(new HashMap<>());
        cartRepository.save(cart);
    }

    @Override
    public UserDto authenticateUser(UserDto userDto) throws InvalidLoginException {
        User existingUser = userRepository
                .findByUsername(userDto.getUsername())
                .orElseThrow(() -> new InvalidLoginException(INVALID_USERNAME_ERROR_MESSAGE));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(userDto.getPassword(), existingUser.getPassword())) {
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
}
