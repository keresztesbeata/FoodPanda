package app.service.impl;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.UserMapper;
import app.model.User;
import app.model.UserRole;
import app.repository.UserRepository;
import app.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String INVALID_USERNAME_ERROR_MESSAGE = "Invalid username!\n The username cannot be null!";
    private static final String DUPLICATE_USERNAME_ERROR_MESSAGE = "Duplicate username!\n This username is already taken!";

    @Autowired
    private UserRepository userRepository;

    private UserMapper userMapper = new UserMapper();

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.toDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Integer id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .map(user -> userMapper.toDto(user))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserDto getUserByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> userMapper.toDto(user))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void addUser(UserDto userDto) throws InvalidDataException, DuplicateDataException{
        if(userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new InvalidDataException(INVALID_USERNAME_ERROR_MESSAGE);
        }
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new DuplicateDataException(DUPLICATE_USERNAME_ERROR_MESSAGE);
        }
        User user = userMapper.toEntity(userDto);
        user.setUserRole(UserRole.USER);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

}
