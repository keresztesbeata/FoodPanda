package app.exceptions;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException() {
        super("The requested entity could not be found in the database.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
