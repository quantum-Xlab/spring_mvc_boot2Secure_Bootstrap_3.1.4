package com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;


@Entity
@Table(name = "users")
@JsonPropertyOrder({"user_id", "user_name", "password", "first_name", "last_name", "age"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "user_name")
    @JsonProperty("user_name")
    private String userName;

    @Column(name = "password")
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JsonManagedReference

    //@LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.JOIN)

    @JoinTable(name = "users_with_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }
    @JsonCreator
    public User(@JsonProperty(value = "user_name", required = true) String userName, String password, String firstName, String lastName, Integer age) {

        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;


    }



    @Override
    public String getUsername() {
        return this.userName;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    public Long getUserId() {
        return this.id;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public Integer getUserAge() {
        return this.age;
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

    public void setUsername(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setAge(Integer age) {
        this.age = age;
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
