package com.mokhovav.base.databases;

public interface DBSettings {
    String getConnectionString() ;
    String getUsername();
    String getPassword();
    String getDataBaseName();
}
