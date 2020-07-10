package com.mokhovav.base.databases.SQL;

import com.mokhovav.base.databases.BaseUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseUser {
    public User() {
    }

    public User(
            @NotBlank(message = "Login cannot be empty") String login,
            @NotBlank(message = "Password cannot be empty") String password,
            boolean needToChange
    ) {
        super(login, password, needToChange);
    }

    public User(
            @NotBlank(message = "Login cannot be empty") String login,
            @NotBlank(message = "Password cannot be empty") String password,
            boolean needToChange,
            String firstName,
            String lastName,
            Set<Groups> groups
    ) {
        super(login, password, needToChange);
        this.firstName = firstName;
        this.lastName = lastName;
        this.groups = groups;
    }

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(targetEntity = Groups.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "users_groups", joinColumns = @JoinColumn(name = "users_id"))
    @Column(
            name = "groups_id",
            nullable = false
    )
    private Set<Groups> groups;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }
}