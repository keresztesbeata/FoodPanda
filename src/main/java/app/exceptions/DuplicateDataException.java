package app.exceptions;

public class DuplicateDataException extends RuntimeException{
    public DuplicateDataException() {
        super("Duplicate data!");
    }

    public DuplicateDataException(String message) {
        super(message);
    }
}
