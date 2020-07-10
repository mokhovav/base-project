package com.mokhovav.base.databases;

public interface HibernateSettings {
    String getDriverClassName() ;
    String getDialect();
    String getDdlAuto();
    String[] getPackagesToScan();
}
