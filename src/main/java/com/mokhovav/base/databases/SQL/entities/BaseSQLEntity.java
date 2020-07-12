package com.mokhovav.base.databases.SQL.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@MappedSuperclass
public class BaseSQLEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            updatable = false,
            unique = true
    )
    private Long id;

    public BaseSQLEntity() {
    }

    public Long getId() {
        return id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
