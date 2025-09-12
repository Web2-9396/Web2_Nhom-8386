package com.example.bttuan3.controllers;

import com.example.bttuan3.models.Company;
import com.example.bttuan3.models.Role;
import com.example.bttuan3.models.User;
import com.example.bttuan3.repository.CompanyRepository;
import com.example.bttuan3.repository.PositionRepository;
import com.example.bttuan3.repository.RoleRepository;
import com.example.bttuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.bttuan3.models.Position;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired private UserRepository userRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private CompanyRepository companyRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private PositionRepository positionRepo;
    

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


    @GetMapping("/add")
    public String showAddForm(Model model) {
        List<Role> roles = roleRepo.findAll();
        List<Company> companies = companyRepo.findAll();

        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);
        model.addAttribute("companies", companies);
        model.addAttribute("positions", positionRepo.findAll());

        return "add-user"; 
    }


    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(required = false) Long companyId) {
        if (companyId != null) {
            Company company = companyRepo.findById(companyId).orElse(null);
            user.setCompany(company);
        }
        // mã hóa password trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return "redirect:/users";
    }

    // xóa user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.getRoles().clear();
            userRepo.delete(user);
        }
        return "redirect:/users";
    }

    // gán role cho user
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

    // trang edit user
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
        model.addAttribute("positions", positionRepo.findAll());

        return "edit-user";
    }

    // cập nhật user
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User updatedUser,
                             @RequestParam(required = false) String newPassword) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());

            if (newPassword != null && !newPassword.isBlank()) {
                user.setPassword(passwordEncoder.encode(newPassword));
            }

            if (updatedUser.getCompany() != null && updatedUser.getCompany().getId() != null) {
                Company company = companyRepo.findById(updatedUser.getCompany().getId()).orElse(null);
                user.setCompany(company);
            }
            if (updatedUser.getPosition() != null && updatedUser.getPosition().getId() != null) {
            Position position = positionRepo.findById(updatedUser.getPosition().getId()).orElse(null);
            user.setPosition(position);
        }
            userRepo.save(user);
        }
        return "redirect:/users";
    }
}
