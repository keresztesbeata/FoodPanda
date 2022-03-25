package app.service.validator;

import app.exceptions.InvalidDataException;

public interface Validator<T> {
    void validate(T entity) throws InvalidDataException;
}
