package com.sekin.spring.spring_mvc_boot2Secure.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.*;


@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    //@LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.JOIN)

    @JoinTable(name = "users_with_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {

    }

    public User(String userName, String password) {
        this.userName = userName;

        this.password = password;
    }

    public Long getUserId() {
        return this.id;
    }


    public void setUsername(String userName) {
        this.userName = userName;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = this.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public String rolesToString() {
        Set<Role> roleSet = getRoles();
        StringBuilder result = new StringBuilder();
        roleSet.forEach(s -> result
                .append(s.getName()
                        .replaceAll("ROLE_", ""))
                .append(", "));
        return result
                .deleteCharAt(result.length() - 2)
                .toString();
    }
}
