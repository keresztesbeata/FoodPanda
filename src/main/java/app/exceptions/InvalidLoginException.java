package app.exceptions;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException() {
        super("Invalid username or password!");
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
