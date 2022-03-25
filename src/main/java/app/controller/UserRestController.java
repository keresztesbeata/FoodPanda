package app.controller;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/id/{id}")
    public UserDto getUserById(@PathVariable Integer id) throws EntityNotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("/user/username")
    public UserDto getUserByUsername(@RequestParam String username) throws EntityNotFoundException {
        return userService.getUserByUsername(username);
    }

    @PostMapping(value = "/user/register")
    public void register(@RequestBody UserDto userDto) throws InvalidDataException, DuplicateDataException {
        userService.addUser(userDto);
    }

    @PostMapping(value = "/user/login")
    public UserDto login(@RequestBody UserDto userDto) throws InvalidDataException, DuplicateDataException {
        return userService.authenticateUser(userDto);
    }
}
