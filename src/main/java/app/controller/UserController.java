package app.controller;

import app.dto.UserDto;
import app.dto.UserMapper;
import app.exceptions.DuplicateUsernameException;
import app.exceptions.EntityNotFoundException;
import app.model.User;
import app.model.UserRole;
import app.repository.UserRepository;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/all")
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/id/{id}")
    public UserDto getUserById(@PathVariable Integer id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("/user/username")
    public @ResponseBody UserDto getUserByUsername(@RequestBody UserDto userDto) throws EntityNotFoundException {
        return userRepository.findByUsername(userDto.getUsername())
                .map(UserMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping(value = "/user/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody UserDto addUser(@RequestBody UserDto userDto) throws DuplicateUsernameException {
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
