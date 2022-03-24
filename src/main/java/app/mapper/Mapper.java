package app.mapper;

public interface Mapper<T,U> {
    U toDto(T t);

    T toEntity(U u);
}
