package com.mokhovav.base.databases.noSQL;

import com.mokhovav.base.databases.noSQL.entities.BaseNoSQLEntity;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(
        value = "project.config.MongoDBEnable",
        havingValue = "true")
public class MongoDBService implements NoSQLService {

    @Autowired
    MongoTemplate template;

    @Override
    public String save(BaseNoSQLEntity object) {
        return Optional.ofNullable(template.insert(object))
                .map(BaseNoSQLEntity::getId)
                .orElse(null);
    }

    @Override
    public BaseNoSQLEntity findById(String id, Class collection) {
        return (BaseNoSQLEntity) template.findById(id, collection);
    }

    @Override
    public DeleteResult delete(BaseNoSQLEntity object) {
        return template.remove(object);
    }

    @Override
    public List findAll(Class collection) {
        return template.findAll(collection);
    }

    @Override
    public void dropCollection(Class collection) {
        template.dropCollection(collection);
    }

    @Override
    public String update(BaseNoSQLEntity object) {
        return Optional.ofNullable(template.save(object))
                .map(BaseNoSQLEntity::getId)
                .orElse(null);
    }
}
