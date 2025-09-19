package com.example.bttuan3.controllers;

import com.example.bttuan3.models.Company;
import com.example.bttuan3.models.Position;
import com.example.bttuan3.models.Role;
import com.example.bttuan3.models.User;
import com.example.bttuan3.repository.CompanyRepository;
import com.example.bttuan3.repository.PositionRepository;
import com.example.bttuan3.repository.RoleRepository;
import com.example.bttuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class api_Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private RoleRepository roleRepository;

    // ==================== USER CRUD ====================

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User userDetails) {
        // Lấy Company từ DB
        Company company = companyRepository.findById(userDetails.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        userDetails.setCompany(company);

        // Lấy Position từ DB
        if (userDetails.getPosition() != null) {
            Position position = positionRepository.findById(userDetails.getPosition().getId())
                    .orElseThrow(() -> new RuntimeException("Position not found"));
            userDetails.setPosition(position);
        }

        // Lấy Roles từ DB
        if (userDetails.getRoles() != null && !userDetails.getRoles().isEmpty()) {
            Set<Role> roles = userDetails.getRoles().stream()
                    .map(r -> roleRepository.findById(r.getId())
                            .orElseThrow(() -> new RuntimeException("Role not found with id " + r.getId())))
                    .collect(Collectors.toSet());
            userDetails.setRoles(roles);
        }

        return userRepository.save(userDetails);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());

        // Update Company
        Company company = companyRepository.findById(userDetails.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        user.setCompany(company);

        // Update Position
        if (userDetails.getPosition() != null) {
            Position position = positionRepository.findById(userDetails.getPosition().getId())
                    .orElseThrow(() -> new RuntimeException("Position not found"));
            user.setPosition(position);
        }

        // Update Roles
        if (userDetails.getRoles() != null && !userDetails.getRoles().isEmpty()) {
            Set<Role> roles = userDetails.getRoles().stream()
                    .map(r -> roleRepository.findById(r.getId())
                            .orElseThrow(() -> new RuntimeException("Role not found with id " + r.getId())))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "Deleted user id: " + id;
    }

    // ==================== COMPANY CRUD ====================

    @GetMapping("/companies")
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @PostMapping("/companies")
    public Company createCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PutMapping("/companies/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        company.setCompanyName(companyDetails.getCompanyName());
        company.setUsers(companyDetails.getUsers());
        return companyRepository.save(company);
    }

    @DeleteMapping("/companies/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
        return "Deleted company id: " + id;
    }
}

