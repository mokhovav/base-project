package com.mokhovav.base.databases.SQL;

import com.mokhovav.base.databases.DatabaseSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("PostgreSQL")
public class PostgreSQLSettings implements HibernateSettings {
    @Autowired
    Environment env;

    @Override
    public String getDriverClassName() {
        return env.getProperty("hibernate.driver-class-name", "org.postgresql.Driver");
    }

    @Override
    public String[] getPackagesToScan() {
        return new String[]{
                env.getProperty("project.config.SQLDBEntities", "com.mokhovav.base.databases.SQL"),
                "com.mokhovav.base.databases.SQL.JUnit"
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
