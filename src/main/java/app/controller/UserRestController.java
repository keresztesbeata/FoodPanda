package app.controller;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.model.User;
import app.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/id/{id}")
    public UserDto getUserById(@PathVariable Integer id) throws EntityNotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("/users/username")
    public UserDto getUserByUsername(@RequestParam String username) throws EntityNotFoundException {
        return userService.getUserByUsername(username);
    }

    @PostMapping(value = "/process_register")
    public ModelAndView register(@RequestBody UserDto userDto) throws InvalidDataException, DuplicateDataException {
        userService.addUser(userDto);
        return new ModelAndView("home", "user", userDto);
    }

    @PostMapping(value = "/process_login")
    public ModelAndView login(@RequestBody UserDto userDto) throws InvalidDataException, DuplicateDataException {
        UserDto loggedInUser = userService.authenticateUser(userDto);
        return new ModelAndView("home", "user", loggedInUser);
    }
}
