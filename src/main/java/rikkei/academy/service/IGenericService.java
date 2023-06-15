package rikkei.academy.service;

import java.util.List;


public interface IGenericService<T, E> {
    List<T> findAll();

    T save(T t);

    void deleteById(E id);

    T findById(E id);
}
