package com.mokhovav.base.databases.SQL.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
public class BaseUser extends BaseSQLEntity {

    @Column(
            name = "login",
            unique=true
    )
    @NotBlank(message = "Login cannot be empty")
    private String login;

    @JsonIgnore
    @Column(name = "password")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Column(name = "need_to_change")
    private boolean needToChange = false;

    public BaseUser() {
    }

    public BaseUser(
            @NotBlank(message = "Login cannot be empty") String login,
            @NotBlank(message = "Password cannot be empty") String password,
            boolean needToChange
    ) {
        this.login = login;
        this.password = password;
        this.needToChange = needToChange;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNeedToChange() {
        return needToChange;
    }

    public void setNeedToChange(boolean needToChange) {
        this.needToChange = needToChange;
    }
}
