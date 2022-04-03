package app.controller;

import app.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageNavigationController {

    @GetMapping(WebPageUrlRegistry.GOTO_LOGIN_URL)
    public String goToLogin(Model model) {
        model.addAttribute("user", new UserDto());
        return WebPageUrlRegistry.LOGIN_PAGE;
    }
}
