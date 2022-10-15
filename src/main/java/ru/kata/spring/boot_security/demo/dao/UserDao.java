package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Component
public interface UserDao {

    void addUser(User user, int role);

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    void editUser(User user, int id, int role);

    void deleteUser(int id);
}
