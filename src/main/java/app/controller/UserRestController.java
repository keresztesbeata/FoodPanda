package app.controller;

import app.config.JwtTokenProvider;
import app.dto.UserDto;
import app.exceptions.DuplicateDataException;
import app.exceptions.EntityNotFoundException;
import app.exceptions.InvalidDataException;
import app.mapper.UserMapper;
import app.model.User;
import app.model.UserRole;
import app.repository.UserRepository;
import app.service.api.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static app.controller.Utils.getCurrentUser;


@RestController
@Log4j2
public class UserRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @GetMapping(value = "/current_user")
    @ResponseBody
    public ResponseEntity getLoggedInUser() {
        try {
            UserMapper userMapper = new UserMapper();
            return ResponseEntity.ok().body(userMapper.toDto(getCurrentUser()));
        } catch (EntityNotFoundException e) {
            log.warn("UserRestController: getLoggedInUser {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(userService.getUserByUsername(username));
        } catch (EntityNotFoundException e) {
            log.warn("UserRestController: getUserByUsername {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/perform_login")
    public ResponseEntity authenticateUser(@RequestBody UserDto userDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getUsername(),
                            userDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok().body(new JwtAuthenticationResponse(jwt));
        } catch (AuthenticationException e) {
            log.error("UserRestController: authenticateUser {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping(value = "/perform_register")
    public ResponseEntity register(@RequestBody UserDto userDto, @RequestParam Boolean asAdmin) {
        try {
            userDto.setUserRole(asAdmin ? UserRole.ADMIN.name() : UserRole.CUSTOMER.name());
            userService.addUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (InvalidDataException | DuplicateDataException e) {
            log.error("UserRestController: register {} ", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

}
