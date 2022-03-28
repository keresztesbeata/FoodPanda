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

    @PostMapping(value = "/process_register")
    public ModelAndView register(@RequestBody UserDto userDto) throws InvalidDataException, DuplicateDataException {
        userService.addUser(userDto);
        return new ModelAndView("home", "user", userDto);
    }

    @PostMapping(value = "/process_login")
    public ModelAndView login(@RequestBody UserDto userDto) throws InvalidDataException, DuplicateDataException {
        UserDto loggedInUser = userService.authenticateUser(userDto);
        if (loggedInUser.getUserRole().equals(UserRole.ADMIN.name())) {
            return new ModelAndView("home", "admin", loggedInUser);
        }
        return new ModelAndView("home", "customer", loggedInUser);
    }
}
