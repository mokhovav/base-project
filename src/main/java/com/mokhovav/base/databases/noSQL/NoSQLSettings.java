package com.mokhovav.base.databases.noSQL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("noSQLSettings")
public class NoSQLSettings implements NoSQLDBSettings {
    @Autowired
    Environment env;

    @Override
    public String getConnectionString() {
        return  getPrefix() + "://" +
                getUsername() +
                ":" + getPassword() +
                "@" + env.getProperty("noSQL.host") +
                ":" + env.getProperty("noSQL.port", Integer.class);
    }

    @Override
    public String getUsername() {
        return env.getProperty("noSQL.username");

    }

    @Override
    public String getPassword() {
        return env.getProperty("noSQL.password");

    }

    @Override
    public String getDataBaseName() {
        return env.getProperty("noSQL.database");
    }

    @Override
    public String getPrefix() {
        return env.getProperty("noSQL.prefix");
    }
}
