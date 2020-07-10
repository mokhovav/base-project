package com.mokhovav.base.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("PostgreSQLSettings")
public class PostgreSQLSettings implements DatabaseSettings, HibernateSettings {
    @Autowired
    Environment env;

    @Override
    public String getDriverClassName() {
        return env.getProperty("hibernate.driver-class-name", "org.postgresql.Driver");
    }

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

    @Override
    public String[] getPackagesToScan() {
        return new String[]{
                env.getProperty("project.config.SQLDBEntities", "com.mokhovav.base.databases.SQL"),
                "com.mokhovav.base.JUnit"
        };
    }

    @Override
    public String getDialect() {
        return env.getProperty("hibernate.sql-dialect", "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Override
    public String getDdlAuto() {
        return env.getProperty("hibernate.ddl-auto", "update");
    }
}
