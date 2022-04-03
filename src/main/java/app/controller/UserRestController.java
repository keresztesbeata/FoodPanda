package app.controller;

import app.dto.UserDto;
import app.exceptions.*;
import app.model.User;
import app.model.UserRole;
import app.service.api.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public UserDto getUserByUsername(@RequestParam String username) throws EntityNotFoundException {
        return userService.getUserByUsername(username);
    }

    @PostMapping(value = "/perform_register")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        try {
            userDto.setUserRole(UserRole.CUSTOMER.name());
            userService.addUser(userDto);
            return ResponseEntity.ok().body(userDto);
        }catch (InvalidDataException | DuplicateDataException e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping(value = "/perform_login")
    public ResponseEntity login(@RequestBody UserDto userDto) {
        try {
            UserDto user = userService.authenticateUser(userDto);
            return ResponseEntity.ok().body(user);
        }catch(InvalidDataException | InvalidLoginException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping(value = "/perform_logout")
    public ResponseEntity logout(@RequestParam String username) {
        try{
            userService.logout(username);
            return ResponseEntity.ok().build();
        }catch(InvalidOperationException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
