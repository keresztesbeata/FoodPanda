package app.service.impl;

import app.config.UserDetailsImpl;
import app.config.UserDetailsServiceImpl;
import app.dto.UserDto;
import app.exceptions.*;
import app.mapper.UserMapper;
import app.model.Cart;
import app.model.User;
import app.model.UserRole;
import app.model.UserSession;
import app.repository.CartRepository;
import app.repository.UserRepository;
import app.repository.UserSessionRepository;
import app.service.api.UserService;
import app.service.validator.UserDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String DUPLICATE_USERNAME_ERROR_MESSAGE = "Duplicate username!\nThis username is already taken!";
    private static final String INVALID_PASSWORD_ERROR_MESSAGE = "Invalid password!";
    private static final String NO_SIGNED_IN_USER_ERROR_MESSAGE = "Cannot log out! There is no currently logged in user!";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserSessionRepository userSessionRepository;

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

        if (user.getUserRole().equals(UserRole.CUSTOMER)) {
            // create cart for new customers
            Cart cart = new Cart();
            cart.setCustomer(savedUser);
            cartRepository.save(cart);
        }
    }

    @Override
    public void logout(User user) throws InvalidOperationException {
        // todo
    }
}
