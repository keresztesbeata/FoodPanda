package app.controller;

import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.model.UserRole;
import app.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/username")
    public UserDto getUserByUsername(@RequestParam String username) throws EntityNotFoundException {
        return userService.getUserByUsername(username);
    }

    @PostMapping(value = "/register")
    public ModelAndView register(@RequestBody UserDto userDto) throws InvalidDataException, DuplicateDataException {
        userService.addUser(userDto);
        return new ModelAndView("login", "user", userDto);
    }

    @PostMapping(value = "/login")
    public ModelAndView login(@RequestBody UserDto userDto) throws InvalidDataException, DuplicateDataException {
        UserDto loggedInUser = userService.authenticateUser(userDto);
        return new ModelAndView("home", "user", loggedInUser);
    }
}
