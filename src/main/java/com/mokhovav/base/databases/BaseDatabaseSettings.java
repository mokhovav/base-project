package com.mokhovav.base.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("DatabaseSettings")
public class BaseDatabaseSettings implements DatabaseSettings {
    @Autowired
    Environment env;

    @Override
    public String getConnectionString() {
        return "jdbc:postgresql://" +
                env.getProperty("hibernate.host", "localhost") +
                ":" + env.getProperty("hibernate.port", Integer.class , 5432) +
                "/" + getDataBaseName();
    }

    @Override
    public String getUsername() {
        return env.getProperty("hibernate.username", "user");
    }

    @Override
    public String getPassword() {
        return env.getProperty("hibernate.password", "user");
    }

    @Override
    public String getDataBaseName() {
        return env.getProperty("hibernate.database", "projectDB");
    }
}
