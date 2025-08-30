package com.example.bttuan3.controllers;

import com.example.bttuan3.models.Role;
import com.example.bttuan3.models.User;
import com.example.bttuan3.repository.RoleRepository;
import com.example.bttuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
    }

   @PostMapping("/{userId}/roles/{roleId}")
        public User assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
            User user = userRepo.findById(userId).orElse(null);
            Role role = roleRepo.findById(roleId).orElse(null);
    if (user != null && role != null) {
            Set<Role> roles = user.getRoles();
            roles.add(role);
            user.setRoles(roles);
            return userRepo.save(user);
        }
        return null;
    }
}
