package com.mokhovav.base.databases.SQL;

public interface HibernateSettings {
    String getDriverClassName() ;
    String getDialect();
    String getDdlAuto();
    String[] getPackagesToScan();
}
