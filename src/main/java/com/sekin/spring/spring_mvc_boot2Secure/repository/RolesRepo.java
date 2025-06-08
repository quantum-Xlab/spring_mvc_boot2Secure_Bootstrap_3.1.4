package com.sekin.spring.spring_mvc_boot2Secure.repository;

import com.sekin.spring.spring_mvc_boot2Secure.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Role, Long> {
    Role findByName(String rolename);

}