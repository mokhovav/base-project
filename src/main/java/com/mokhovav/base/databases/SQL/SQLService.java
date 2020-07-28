package com.mokhovav.base.databases.SQL;

import java.util.List;

public interface SQLService<T> {
    Long save(T object);
    boolean update(T object);
    boolean delete(T object);
    T getById(Long id, Class<T> c);
    T findObject(String text);
    List<T> findList(String text);
    List<T> findAll(Class c);
}
