package com.sekin.spring.spring_mvc_boot2Secure.service;

import com.sekin.spring.spring_mvc_boot2Secure.model.Role;
import com.sekin.spring.spring_mvc_boot2Secure.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> listUsers();
    void saveUser(User user, String... userRole);
    void saveUser(User user, Set<Role> roles);
    void update(String userName, String newName,
                       String newPassword, String... newRoles);
    void deleteById(Long id);
}
