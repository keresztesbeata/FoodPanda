package app.service.validator;

import app.exceptions.InvalidDataException;

/**
 * Validator for the data related to a given entity
 *
 * @param <T> the type of the given entity
 */
public interface DataValidator<T> {
    /**
     * Validate the data related to an entity.
     *
     * @param entity the entity whose data is checked
     * @throws InvalidDataException if the entity contains invalid data
     */
    void validate(T entity) throws InvalidDataException;
}
