package com.sekin.spring.spring_mvc_boot2Secure.service;

import com.sekin.spring.spring_mvc_boot2Secure.model.Role;
import com.sekin.spring.spring_mvc_boot2Secure.model.User;
import com.sekin.spring.spring_mvc_boot2Secure.repository.RolesRepo;
import com.sekin.spring.spring_mvc_boot2Secure.repository.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserDetailsService {

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

    @Transactional
    public List<User> listUsers() {
        return usersRepo.findAll();
    }

    @Transactional
    public void saveUser(User user, String... userRole) {
        Set<Role> role = new HashSet<>();
        Stream.of(userRole).forEach(r -> role.add(new Role(r)));
        //  role.forEach(x -> x.setUsers(Collections.singleton(user)));;
        user.setRoles(role);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        usersRepo.save(user);
        rolesRepo.saveAll(role);
    }

    @Transactional
    public void update(String userName, String newName,
                       String newPassword, String... newRoles) {
        User updUser = (User) loadUserByUsername(userName);
        Set<Role> role;
        if (!newName.isBlank()) {
            updUser.setUsername(newName);
        }
        if (!newPassword.isBlank()) {
            updUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        }
        if (newRoles.length > 0) {
            role = new HashSet<>();
            Stream.of(newRoles).forEach(r -> role.add(new Role(r)));
            //role.forEach(x -> x.setUsers(Collections.singleton(updUser)));;
            updUser.setRoles(role);
            rolesRepo.saveAll(role);
        }
        usersRepo.save(updUser);
    }

    @Transactional
    public void deleteById(Long id) {
        usersRepo.deleteById(id);
    }

}
