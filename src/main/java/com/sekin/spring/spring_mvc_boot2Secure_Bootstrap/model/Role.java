package com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.Set;

@Entity
@Table(name = "users_roles")
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Transient
    private User user;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference

    private Set<User> users;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }


    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setUsers(User user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
