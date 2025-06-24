package com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.repository;

import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :username")
    Optional<User> findByUsername(String username);

}
