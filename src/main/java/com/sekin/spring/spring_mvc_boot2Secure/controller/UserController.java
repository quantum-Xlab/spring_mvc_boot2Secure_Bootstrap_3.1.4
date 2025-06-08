package com.sekin.spring.spring_mvc_boot2Secure.controller;

import com.sekin.spring.spring_mvc_boot2Secure.model.User;
import com.sekin.spring.spring_mvc_boot2Secure.repository.UsersRepo;
import com.sekin.spring.spring_mvc_boot2Secure.service.UserServiceImpl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final UsersRepo usersRepo;


    public UserController(UserServiceImpl userService, UsersRepo usersRepo) {
        this.userService = userService;
        this.usersRepo = usersRepo;
    }

    @GetMapping(value = "/")
    public String printUsers() {
        //Для демонстрации создаем нового рута при входе
        if (usersRepo.findByUsername("root").isEmpty()) {
            User user = new User("root", "root");
            userService.saveUser(user, "ROLE_ADMIN");
        }
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String printUsers(ModelMap model) {
        List<User> users;
        users = userService.listUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String newUser(@RequestParam(value = "user_name") String userName,
                          @RequestParam(value = "password") String userPassword,
                          @RequestParam(value = "roles", required = false, defaultValue = "ROLE_USER") String... userRoles) {
        User user = new User(userName, userPassword);
        userService.saveUser(user, userRoles);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(@RequestParam(value = "editUser") String userName, ModelMap model) {
        User user = (User) userService.loadUserByUsername(userName);
        model.addAttribute("user", user);
        return "/edit";
    }

    @PostMapping(value = "/admin/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateUser(@RequestParam(value = "userName", required = false) String userName,
                             @RequestParam(value = "newName", required = false, defaultValue = "") String newName,
                             @RequestParam(value = "newPassword", required = false, defaultValue = "") String password,
                             @RequestParam(value = "roles", required = false, defaultValue = "") String... roles) {
        userService.update(userName, newName, password, roles);
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
        return "/user";
    }
}
