package com.mokhovav.base.databases.SQL.JUnit;

import com.mokhovav.base.databases.SQL.entities.BaseSQLEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "junit_test")
public class TestSQLEntity extends BaseSQLEntity {
    @Column(name = "name")
    String name;

    public TestSQLEntity() {
    }

    public TestSQLEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
