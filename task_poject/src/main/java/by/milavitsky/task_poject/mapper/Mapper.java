package by.milavitsky.task_poject.mapper;

public interface Mapper<T, K> {

    T toDTO(K k);

    K fromDTO(T t);
}
