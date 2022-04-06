package app.controller;

import app.dto.UserDto;
import app.exceptions.*;
import app.model.UserRole;
import app.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity getUserByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok().body(userService.getUserByUsername(username));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping(value = "/perform_register")
    public ResponseEntity register(@RequestBody UserDto userDto, @RequestParam Boolean asAdmin) {
        try {
            userDto.setUserRole(asAdmin? UserRole.ADMIN.name(): UserRole.CUSTOMER.name());
            userService.addUser(userDto);
            return ResponseEntity.ok().body(userDto);
        } catch (InvalidDataException | DuplicateDataException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping(value = "/perform_login")
    public ResponseEntity login(@RequestBody UserDto userDto) {
        try {
            UserDto user = userService.authenticateUser(userDto);
            return ResponseEntity.ok().body(user);
        } catch (InvalidDataException | InvalidLoginException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping(value = "/perform_logout")
    public ResponseEntity logout(@RequestParam String username) {
        try {
            userService.logout(username);
            return ResponseEntity.ok().build();
        } catch (InvalidOperationException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
