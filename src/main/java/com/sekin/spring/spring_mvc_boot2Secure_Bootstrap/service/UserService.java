package com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.service;

import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model.Role;
import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> listUsers();
    User saveUser(User user, String... userRole);
    void saveUsera(User user, Set<Role> roles);
    User update(String userName, String newName,
                String newPassword, String newFirstName,
                String newLastName, Integer age, String... newRoles);
    void deleteById(Long id);
}
