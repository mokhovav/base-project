package com.mokhovav.base.databases.SQL;

import com.mokhovav.base.databases.SQL.entities.BaseSQLEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SQLService<T> {
    Long save(T object);
    boolean update(T object);
    boolean delete(T object);
    T getById(Long id, Class<T> c);
    T findObject(String text);
    List<T> findList(String text);
    List<T> findAll(Class c);
}
