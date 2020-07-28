package com.mokhovav.base.databases.noSQL;

import com.mokhovav.base.databases.noSQL.entities.BaseNoSQLEntity;
import com.mongodb.client.result.DeleteResult;

import java.util.List;

public interface NoSQLService<T extends BaseNoSQLEntity>  {
    String save(T object);
    DeleteResult delete(T object);
    String update(T object);
    T findById(String id, Class collection);
    List<T> findAll(Class collection);
    void dropCollection(Class collection);


}
