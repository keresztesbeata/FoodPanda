package app.controller;

import app.dto.UserDto;
import app.dto.UserMapper;
import app.exceptions.DuplicateUsernameException;
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

    @Autowired
    private UserRepository userRepository;

    @GetMapping(REGISTER_URL)
    public String viewRegistrationPage(Model model) {
        UserDto userDto = new UserDto();

        model.addAttribute("user", userDto);

        return REGISTER_PAGE;
    }

    @PostMapping(REGISTER_URL)
    public ModelAndView registerUser(@ModelAttribute("user") UserDto userDto) {
        try {
            if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
                throw new DuplicateUsernameException();
            }
            User user = UserMapper.toEntity(userDto);

            user.setUserRole(UserRole.USER);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            userRepository.save(user);

            return new ModelAndView(HOME_PAGE, "user", userDto);
        } catch (DuplicateUsernameException e) {
            return new ModelAndView(REGISTER_PAGE, "error", e.getMessage());
        }
    }
}
