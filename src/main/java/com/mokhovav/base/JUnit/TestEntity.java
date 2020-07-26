package com.mokhovav.base.JUnit;


import com.mokhovav.base.databases.BaseSQLEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "junit_test")
public class TestEntity extends BaseSQLEntity {
    @Column(name = "name")
    String name;

    public TestEntity() {
    }

    public TestEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
