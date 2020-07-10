package com.mokhovav.base.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("HibernateSettings")
public class PostgreSQLSettings implements SQLDatabaseSettings {
    @Autowired
    Environment env;

    @Override
    public String getDriverClassName() {
        return env.getProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");
    }

    @Override
    public String getUrl() {
        return env.getProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/projectDB");
    }

    @Override
    public String getUsername() {
        return env.getProperty("spring.datasource.username", "user");
    }

    @Override
    public String getPassword() {
        return env.getProperty("spring.datasource.password", "user");
    }

    @Override
    public String getPackagesToScan() {
        return env.getProperty("project.config.SQLDBEntities", "com.mokhovav.base.databases.sql");
    }

    @Override
    public String getDialect() {
        return env.getProperty("spring.jooq.sql-dialect", "org.hibernate.dialect.PostgreSQLDialect");
    }

    @Override
    public String getDdlAuto() {
        return env.getProperty("spring.jpa.hibernate.ddl-auto", "update");
    }
}
