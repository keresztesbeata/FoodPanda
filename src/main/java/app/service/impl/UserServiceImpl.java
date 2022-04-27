package app.service.impl;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.UserMapper;
import app.model.Cart;
import app.model.User;
import app.model.UserRole;
import app.repository.CartRepository;
import app.repository.UserRepository;
import app.service.api.UserService;
import app.service.validator.UserDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final String DUPLICATE_USERNAME_ERROR_MESSAGE = "Duplicate username!\nThis username is already taken!";

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

        if (user.getUserRole().equals(UserRole.CUSTOMER)) {
            // create cart for new customers
            Cart cart = new Cart();
            cart.setCustomer(savedUser);
            cartRepository.save(cart);
        }
    }
}
