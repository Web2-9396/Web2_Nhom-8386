package com.example.bttuan3.controllers;

import com.example.bttuan3.models.Role;
import com.example.bttuan3.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/roles")
public class RoleController {
    @Autowired private RoleRepository roleRepo;

    // Hiển thị danh sách role
    @GetMapping
    public String getAllRoles(Model model) {
        List<Role> roles = roleRepo.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("role", new Role());
        return "roles"; // trả về roles.html
    }

    // Thêm role
    @PostMapping
    public String addRole(@ModelAttribute Role role) {
        roleRepo.save(role);
        return "redirect:/roles";
    }

    // Xóa role
    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id) {
        roleRepo.deleteById(id);
        return "redirect:/roles";
    }
}
