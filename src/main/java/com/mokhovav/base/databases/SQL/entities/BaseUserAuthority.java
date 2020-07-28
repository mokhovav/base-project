package com.mokhovav.base.databases.SQL.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

@MappedSuperclass
public class BaseUserAuthority extends BaseSQLEntity implements GrantedAuthority{

    @Column(
            name = "group_name",
            unique=true
    )
    @Size(max = 25)
    @NotBlank(message = "Group name cannot be empty")
    String authority;

    public BaseUserAuthority() {
    }

    public BaseUserAuthority(@NotBlank(message = "Group name cannot be empty") String authority) {
        this.authority = authority;
    }

    public void setAuthority(String group) {
        this.authority = group;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
