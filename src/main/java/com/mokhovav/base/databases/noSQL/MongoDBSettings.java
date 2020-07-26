package com.mokhovav.base.databases.noSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("MongoDBSettings")
public class MongoDBSettings implements NoSQLDBSettings {
    @Autowired
    Environment env;

    @Override
    public String getConnectionString() {
        return "mongodb://" +
                getUsername() +
                ":" + getPassword() +
                "@" + env.getProperty("mongoDB.host", "mongodb") +
                ":" + env.getProperty("mongoDB.port", Integer.class , 27017);
    }

    @Override
    public String getUsername() {
        return env.getProperty("mongoDB.username", "user");

    }

    @Override
    public String getPassword() {
        return env.getProperty("mongoDB.password", "user");

    }

    @Override
    public String getDataBaseName() {
        return env.getProperty("mongoDB.database", "projectDB");
    }
}
