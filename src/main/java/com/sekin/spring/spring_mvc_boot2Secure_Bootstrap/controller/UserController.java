package com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.controller;

import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model.Role;
import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.model.User;
import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.repository.UsersRepo;
import com.sekin.spring.spring_mvc_boot2Secure_Bootstrap.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final UsersRepo usersRepo;


    public UserController(UserService userService, UsersRepo usersRepo) {
        this.userService = userService;
        this.usersRepo = usersRepo;
    }

    @GetMapping(value = "/")
    public String printUsers() {
        Set<Role> role = new HashSet<>();
        role.add(new Role("ROLE_ADMIN"));
        role.add(new Role("ROLE_USER"));
        //Для демонстрации создаем нового рута при входе
        if (usersRepo.findByUsername("root").isEmpty()) {
            User user = new User("root@mail.ru", "root", "Den", "Sekin", 38);
            userService.saveUser(user, role);
        }
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String printUsers(ModelMap model, Authentication authentication) {
        List<User> users;
        users = userService.listUsers();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String newUser(@RequestParam(value = "user_name") String userName,
                          @RequestParam(value = "password") String userPassword,
                          @RequestParam(value = "firstName") String firstName,
                          @RequestParam(value = "lastName") String lastName,
                          @RequestParam(value = "age", required = false) Integer age,
                          @RequestParam(value = "roles", required = false, defaultValue = "ROLE_USER") String... userRoles) {
        User user = new User(userName, userPassword, firstName, lastName, age);
        userService.saveUser(user, userRoles);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(@RequestParam(value = "editUser") String userName, ModelMap model) {
        User user = (User) userService.loadUserByUsername(userName);
        model.addAttribute("user", user);
        return "/admin";

    }

    @PostMapping(value = "/admin/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@RequestParam(value = "userName", required = false) String userName,
                             @RequestParam(value = "newName", required = false, defaultValue = "") String newName,
                             @RequestParam(value = "newPassword", required = false, defaultValue = "") String password,
                             @RequestParam(value = "newFirstName", required = false) String firstName,
                             @RequestParam(value = "newLastName", required = false) String lastName,
                             @RequestParam(value = "newAge", required = false) Integer age,
                             @RequestParam(value = "roles", required = false, defaultValue = "") String... roles) {
        userService.update(userName, newName, password, firstName, lastName, age, roles);
        return "redirect:/admin";
    }

    @PostMapping(value = "/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@RequestParam(value = "del") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String infoUser(Authentication authentication, ModelMap model) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "/admin";
    }
}
