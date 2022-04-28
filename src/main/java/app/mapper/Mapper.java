package app.mapper;

/**
 * Mapper for entities.
 *
 * @param <T> the type of the entity which represents a relational table in the DB
 * @param <U> the type of the DTO object associated to the given entity with reduced or modified fields
 */
public interface Mapper<T,U> {
    /**
     * Map the given entity to the corresponding dto.
     *
     * @param t the entity to be converted
     * @return the corresponding DTO object
     */
    U toDto(T t);

    /**
     * Convert the given DTO object to its original entity.
     *
     * @param u the DTO object to be converted
     * @return the entity which is described by the given DTO object
     */
    T toEntity(U u);
}
