package app.exceptions;

public class InvalidOperationException extends Exception{
    public InvalidOperationException() {
    }

    public InvalidOperationException(String message) {
        super(message);
    }
}