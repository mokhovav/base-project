package com.mokhovav.base.databases.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("SQLSettings")
public class SQLSettings implements SQLDBSettings {

    @Autowired
    Environment env;

    @Override
    public String getDriverClassName() {
        return env.getProperty("hibernate.driver-class-name");
    }

    @Override
    public String[] getPackagesToScan() {
        return new String[]{
                env.getProperty("project.config.SQLDBEntities"),
                "com.mokhovav.base.databases.SQL.JUnit"
        };
    }

    @Override
    public String getDialect() {
        return env.getProperty("hibernate.sql-dialect");
    }

    @Override
    public String getDdlAuto() {
        return env.getProperty("hibernate.ddl-auto");
    }

    @Override
    public String getConnectionString() {
        return  getPrefix() + "://" +
                env.getProperty("hibernate.host") +
                ":" + env.getProperty("hibernate.port", Integer.class) +
                "/" + getDataBaseName();
    }

    @Override
    public String getUsername() {
        return env.getProperty("hibernate.username");
    }

    @Override
    public String getPassword() {
        return env.getProperty("hibernate.password");
    }

    @Override
    public String getDataBaseName() {
        return env.getProperty("hibernate.database");
    }

    @Override
    public String getPrefix() {
        return env.getProperty("hibernate.prefix");
    }
}
