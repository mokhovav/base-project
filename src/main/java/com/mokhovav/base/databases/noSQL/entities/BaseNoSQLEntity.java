package com.mokhovav.base.databases.noSQL.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseNoSQLEntity {
    @JsonIgnore
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id.isEmpty();
    }
}
