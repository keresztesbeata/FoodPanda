package app.controller;

import app.dto.UserDto;
import app.dto.UserMapper;
import app.exceptions.user.DuplicateUsernameException;
import app.exceptions.EntityNotFoundException;
import app.model.User;
import app.model.UserRole;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Integer id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping(value = "/new")
    public UserDto addUser(@RequestBody UserDto userDto) throws DuplicateUsernameException {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new DuplicateUsernameException();
        }
        User user = UserMapper.toEntity(userDto);
        user.setUserRole(UserRole.USER);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return UserMapper.toDto(userRepository.save(user));
    }

}
