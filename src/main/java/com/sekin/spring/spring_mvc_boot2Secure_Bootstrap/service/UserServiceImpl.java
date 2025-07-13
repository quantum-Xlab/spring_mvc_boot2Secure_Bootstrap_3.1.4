package com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.service;

import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model.Role;
import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model.User;
import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.repository.RolesRepo;
import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.repository.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepo usersRepo;
    private final RolesRepo rolesRepo;

    public UserServiceImpl(UsersRepo usersRepo, RolesRepo rolesRepo) {
        this.usersRepo = usersRepo;
        this.rolesRepo = rolesRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepo.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return usersRepo.findAll();
    }


    @Override
    @Transactional
    public User saveUser(User user, String... roles) {
        Stream.of(roles).forEach(x -> user.addRole(rolesRepo.findByName(x)));
        Stream.of(roles).forEach(x ->
                rolesRepo
                .findByName(x)
                .setUsers(Collections.singleton(user)));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return usersRepo.save(user);
    }

    //Оставим для создания рута
    @Override
    @Transactional
    public void saveUsera(User user, Set<Role> roles) {
        user.setRoles(roles);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        usersRepo.save(user);
        rolesRepo.saveAll(roles);
    }

    @Override
    @Transactional
    public User update(String userName, String newName,
                       String newPassword, String newFirstName, String newLastName,
                       Integer age, String... newRoles) {
        User updUser = (User) loadUserByUsername(userName);
        Set<Role> role;
        if (!newName.isBlank()) {
            updUser.setUsername(newName);
        }
        if (!newPassword.isBlank()) {
            updUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        }
        if (!newFirstName.isBlank()) {
            updUser.setFirstName(newFirstName);
        }
        if (!newFirstName.isBlank()) {
            updUser.setLastName(newLastName);
        }
        if (age!=null) {
            updUser.setAge(age);
        }
        if (newRoles.length > 0) {
            role = new HashSet<>();
            Stream.of(newRoles).forEach(x -> role.add(rolesRepo.findByName(x)));
            updUser.setRoles(role);
        }
        usersRepo.save(updUser);
        return updUser;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        usersRepo.deleteById(id);
    }

}
