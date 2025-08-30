package com.example.bttuan3.controllers;

import com.example.bttuan3.models.Role;
import com.example.bttuan3.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired private RoleRepository roleRepo;

    @GetMapping
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @PostMapping
    public Role addRole(@RequestBody Role role) {
        return roleRepo.save(role);
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleRepo.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleRepo.deleteById(id);
    }
}
