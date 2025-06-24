package com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.repository;

import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Role, Long> {
    Role findByName(String rolename);

}