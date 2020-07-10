package com.mokhovav.base.databases;

public interface SQLDatabaseSettings {
    String getDriverClassName() ;
    String getUrl() ;
    String getUsername();
    String getPassword();
    String getPackagesToScan();
    String getDialect();
    String getDdlAuto();
}
