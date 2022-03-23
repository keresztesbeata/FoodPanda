package app.exceptions;

public class DuplicateUsernameException extends Exception{
    public DuplicateUsernameException() {
        super("This username is already taken!");
    }

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
