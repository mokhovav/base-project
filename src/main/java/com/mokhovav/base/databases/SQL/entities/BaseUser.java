package com.mokhovav.base.databases.SQL.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
public class BaseUser extends BaseSQLEntity {

    @Column(
            name = "username",
            unique=true
    )
    @Size(min = 3, max = 255)
    @NotBlank(message = "Login cannot be empty")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    @Size(min = 5, max = 255)
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Column(
            name = "account_non_expired",
            columnDefinition = "boolean default true"
    )
    private boolean accountNonExpired;

    @Column(
            name = "account_non_locked",
            columnDefinition = "boolean default true"
    )
    private boolean accountNonLocked;

    @Column(
            name = "credential_non_expired",
            columnDefinition = "boolean default true"
    )
    private boolean credentialsNonExpired;

    @Column(
            name = "enabled",
            columnDefinition = "boolean default true"
    )
    private boolean enabled;

    public BaseUser() {
    }

    public BaseUser(
            @NotBlank(message = "Login cannot be empty") String username,
            @NotBlank(message = "Password cannot be empty") String password
    ) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
