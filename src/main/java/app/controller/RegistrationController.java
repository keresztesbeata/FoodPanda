package app.controller;

import app.dto.UserDto;
import app.mapper.UserMapper;
import app.exceptions.DuplicateDataException;
import app.model.User;
import app.model.UserRole;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static app.controller.WebPageCatalogue.*;

@Controller
public class RegistrationController {

    // todo : validate credentials

    private static final String DUPLICATE_USERNAME_ERROR_MESSAGE = "Duplicate username!\n This username is already taken!";

    @Autowired
    private UserRepository userRepository;
    private UserMapper userMapper = new UserMapper();

    @GetMapping(GOTO_REGISTER_URL)
    public String viewRegistrationPage(Model model) {
        UserDto userDto = new UserDto();

        model.addAttribute("user", userDto);

        return REGISTER_PAGE;
    }

    @PostMapping(REGISTER_REQUEST)
    public ModelAndView registerUser(@ModelAttribute("user") UserDto userDto) {
        try {
            if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
                throw new DuplicateDataException(DUPLICATE_USERNAME_ERROR_MESSAGE);
            }
            User user = userMapper.toEntity(userDto);

            user.setUserRole(UserRole.USER);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            userRepository.save(user);

            return new ModelAndView(HOME_PAGE, "user", userDto);
        } catch (DuplicateDataException e) {
            return new ModelAndView(REGISTER_PAGE, "error", e.getMessage());
        }
    }
}
