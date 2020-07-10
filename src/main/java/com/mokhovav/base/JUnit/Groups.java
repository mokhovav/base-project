package com.mokhovav.base.JUnit;

import com.mokhovav.base.databases.BaseUserGroup;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Groups extends BaseUserGroup {
    public Groups() {
    }

    public Groups(@NotBlank(message = "Group name cannot be empty") String group) {
        super(group);
    }

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)         // Bidirectional communication
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
