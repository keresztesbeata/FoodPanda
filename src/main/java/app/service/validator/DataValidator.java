package app.service.validator;

import app.exceptions.InvalidDataException;

public interface DataValidator<T> {
    void validate(T entity) throws InvalidDataException;
}
