package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Principal principal, Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("principalUser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "allUsers";
    }

    @PostMapping("/admin")
    public String addNewUser(@ModelAttribute("newUser") User user, @RequestParam(value = "role") String[] role) {
        userService.addUser(user, role);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/user/{id}")
    public String editUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "role") String[] role) {
        userService.editUser(user, role);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
