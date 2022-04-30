package app.controller;

import app.config.UserDetailsImpl;
import app.exceptions.EntityNotFoundException;
import app.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Utils {

    public static User getCurrentUser() {
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(currentUser instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) currentUser).getUser();
        }else{
            throw new EntityNotFoundException("No logged in user!");
        }
    }
}
