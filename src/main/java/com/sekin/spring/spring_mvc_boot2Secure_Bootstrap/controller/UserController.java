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
            userService.saveUsera(user, role);
        }
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String printUsers(ModelMap model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "admin";
    }


    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String infoUser(Authentication authentication, ModelMap model) {
        return printUsers(model, authentication);
    }
}
