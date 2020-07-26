package com.mokhovav.base.databases.SQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("SQLSettings")
public class SQLSettings implements HibernateSettings {
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
}
