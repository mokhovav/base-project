package com.mokhovav.base.databases;

public interface DatabaseSettings {
    String getConnectionString() ;
    String getUsername();
    String getPassword();
    String getDataBaseName();
}
