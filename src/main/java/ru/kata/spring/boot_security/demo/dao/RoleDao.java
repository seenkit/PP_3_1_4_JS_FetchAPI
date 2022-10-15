package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Component
public interface RoleDao {

    List<Role> getAllRoles();

    Role getRoleById(int id);

    void addRole(Role role);

    Role getRoleByName(String name);
}
