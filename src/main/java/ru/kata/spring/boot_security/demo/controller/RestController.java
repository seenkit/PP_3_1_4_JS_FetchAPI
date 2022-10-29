package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.util.ExceptionResponse;
import ru.kata.spring.boot_security.demo.util.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserNotUpdatedException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserService userService;

    @Autowired
    public RestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder("BAD_REQUEST: \n");
            for (FieldError field : bindingResult.getFieldErrors()) {
                sb.append(field.getField()).append(" - ").append(field.getDefaultMessage())
                        .append("; \n");
            }
            throw new UserNotCreatedException(sb.toString());
        }
        userService.addUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(UserNotCreatedException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/update")
    public ResponseEntity<HttpStatus> editUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder("BAD_REQUEST: \n");
            for (FieldError field : bindingResult.getFieldErrors()) {
                sb.append(field.getField()).append(" - ").append(field.getDefaultMessage())
                        .append("; \n");
            }
            throw new UserNotUpdatedException(sb.toString());
        }
        userService.editUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(UserNotUpdatedException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getAuthUser(Principal principal) {
        return new ResponseEntity<>(userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }
}
