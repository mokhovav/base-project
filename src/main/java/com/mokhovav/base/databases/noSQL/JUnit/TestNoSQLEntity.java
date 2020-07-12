package com.mokhovav.base.databases.noSQL.JUnit;


import com.mokhovav.base.databases.noSQL.entities.BaseNoSQLEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;

@Document(collection = "junit_test")
public class TestNoSQLEntity extends BaseNoSQLEntity {
    @Column(name = "name")
    String name;

    public TestNoSQLEntity() {
    }

    public TestNoSQLEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
