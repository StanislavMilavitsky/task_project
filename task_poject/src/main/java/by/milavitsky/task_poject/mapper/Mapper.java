package by.milavitsky.task_poject.mapper;

public interface Mapper<T, K> {

    T toDto(K k);

    K fromDto (T t);
}
