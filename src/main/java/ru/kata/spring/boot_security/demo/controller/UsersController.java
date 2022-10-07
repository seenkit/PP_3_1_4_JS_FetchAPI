package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class UsersController {

    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "allUsers";
    }

    @GetMapping("/admin/add-new-user")
    public String getNewUserForm(Model model) {
        model.addAttribute("newUser", new User());
        return "addUserForm";
    }

    @PostMapping("/admin")
    public String addNewUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addUserForm";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/user/{id}/edit-user")
    public String getEditUserForm(Model model, @PathVariable("id") int id) {
        model.addAttribute("editUser", userService.getUserById(id));
        return "/editUserForm";
    }

    @PatchMapping("/admin/user/{id}")
    public String editUser(@ModelAttribute("editUser") @Valid User user, BindingResult bindingResult,
                           @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "/editUserForm";
        }
        userService.editUser(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user/{username}")
    public String showUser(Model model, @PathVariable("username") String username) {
        model.addAttribute("user", userService.getUserByUsername(username));
        return "showUser";
    }
}
