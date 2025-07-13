package com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.controller;


import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model.User;
import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.repository.UsersRepo;
import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

    private final UserService userService;
    private final UsersRepo usersRepo;


    public UserRestController(UserService userService, UsersRepo usersRepo) {
        this.userService = userService;
        this.usersRepo = usersRepo;
    }

    @GetMapping(value = "/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> printUsers() {
        List<User> users;
        users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/admin/edit/{userName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> editUser(@PathVariable String userName) {
        User user = (User) userService.loadUserByUsername(userName);
       return ResponseEntity.ok(user);
    }

@PostMapping(value = "/admin/update")
@PreAuthorize("hasRole('ADMIN')")
public void updateUser(@RequestParam(value = "userName", required = false) String userName,
                         @RequestParam(value = "newName", required = false, defaultValue = "") String newName,
                         @RequestParam(value = "newPassword", required = false, defaultValue = "") String password,
                         @RequestParam(value = "newFirstName", required = false) String firstName,
                         @RequestParam(value = "newLastName", required = false) String lastName,
                         @RequestParam(value = "newAge", required = false) Integer age,
                         @RequestParam(value = "roles", required = false, defaultValue = "") String... roles) {

    userService.update(userName, newName, password, firstName, lastName, age, roles);
}


    @PostMapping(value = "/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@RequestParam(value = "del") Long id) {
        userService.deleteById(id);
     }
    @PostMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User>  newUser(@RequestParam(value = "user_name") String userName,
                                                   @RequestParam(value = "password") String userPassword,
                                                   @RequestParam(value = "firstName") String firstName,
                                                   @RequestParam(value = "lastName") String lastName,
                                                   @RequestParam(value = "age", required = false) Integer age,
                                                   @RequestParam(value = "roles", required = false, defaultValue = "ROLE_USER") String... userRoles) {
        User user = new User(userName, userPassword, firstName, lastName, age);
        userService.saveUser(user, userRoles);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/admin/user")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<User> infoUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

}
