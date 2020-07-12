package com.mokhovav.base.databases.SQL.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
public class BaseUserGroup extends BaseSQLEntity{

    @Column(
            name = "group_name",
            unique=true
    )
    @NotBlank(message = "Group name cannot be empty")
    String group;

    public BaseUserGroup() {
    }

    public BaseUserGroup(@NotBlank(message = "Group name cannot be empty") String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
