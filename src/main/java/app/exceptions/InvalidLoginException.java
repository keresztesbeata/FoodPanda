package app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException() {
        super("Invalid username or password!");
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
