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

    @Autowired 
    private RoleRepository roleRepo;

    // Hiển thị tất cả role
    @GetMapping
    public String getAllRoles(Model model) {
        List<Role> roles = roleRepo.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("role", new Role());
        return "roles";
    }


    @PostMapping
    public String addRole(@ModelAttribute Role role) {
        roleRepo.save(role);
        return "redirect:/roles";
    }
}
