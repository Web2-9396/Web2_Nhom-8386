package com.example.bttuan3.controllers;

import com.example.bttuan3.models.Company;
import com.example.bttuan3.models.Role;
import com.example.bttuan3.models.User;
import com.example.bttuan3.repository.CompanyRepository;
import com.example.bttuan3.repository.RoleRepository;
import com.example.bttuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private CompanyRepository companyRepo;


    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userRepo.findAll();
        List<Role> roles = roleRepo.findAll();
        List<Company> companies = companyRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("companies", companies);
        model.addAttribute("user", new User());
        return "users";
    }

    @PostMapping
    public String addUser(@ModelAttribute User user) {
        if (user.getCompany() != null && user.getCompany().getId() != null) {
            Company company = companyRepo.findById(user.getCompany().getId()).orElse(null);
            user.setCompany(company);
        }
        userRepo.save(user);
        return "redirect:/users";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.getRoles().clear();
            userRepo.delete(user);
        }
        return "redirect:/users";
    }


    @PostMapping("/assign-role")
    public String assignRoleForm(@RequestParam Long userId, @RequestParam Long roleId) {
        User user = userRepo.findById(userId).orElse(null);
        Role role = roleRepo.findById(roleId).orElse(null);
        if (user != null && role != null) {
            Set<Role> roles = user.getRoles();
            roles.add(role);
            user.setRoles(roles);
            userRepo.save(user);
        }
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/users";
        }
        List<Role> roles = roleRepo.findAll();
        List<Company> companies = companyRepo.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("companies", companies);
        return "edit-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());

            if (updatedUser.getCompany() != null && updatedUser.getCompany().getId() != null) {
                Company company = companyRepo.findById(updatedUser.getCompany().getId()).orElse(null);
                user.setCompany(company);
            }

            userRepo.save(user);
        }
        return "redirect:/users";
    }

}
