package com.example.btvn_tuan6.services.impl;

import com.example.btvn_tuan6.models.Company;
import com.example.btvn_tuan6.models.Position;
import com.example.btvn_tuan6.models.Role;
import com.example.btvn_tuan6.models.User;
import com.example.btvn_tuan6.repository.CompanyRepository;
import com.example.btvn_tuan6.repository.PositionRepository;
import com.example.btvn_tuan6.repository.RoleRepository;
import com.example.btvn_tuan6.repository.UserRepository;
import com.example.btvn_tuan6.services.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PositionRepository positionRepo;
    private final CompanyRepository companyRepo;

    public UserServiceImpl(UserRepository userRepo,
            RoleRepository roleRepo,
            PositionRepository positionRepo,
            CompanyRepository companyRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.positionRepo = positionRepo;
        this.companyRepo = companyRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

    }

    @Override
    public User createUser(User userDetails) {
        // Lấy Company từ DB
        Company company = companyRepo.findById(userDetails.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        userDetails.setCompany(company);

        // Lấy Position từ DB
        if (userDetails.getPosition() != null) {
            Position position = positionRepo.findById(userDetails.getPosition().getId())
                    .orElseThrow(() -> new RuntimeException("Position not found"));
            userDetails.setPosition(position);
        }

        // Lấy Roles từ DB
        Set<Role> roles = new HashSet<>();
        if (userDetails.getRoles() != null && !userDetails.getRoles().isEmpty()) {
            roles = userDetails.getRoles().stream()
                    .map(r -> roleRepo.findById(r.getId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id " + r.getId())))
                    .collect(Collectors.toSet());
        } else {
            Role defaultRole = roleRepo.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Default role USER not found"));
            roles.add(defaultRole);
        }
        userDetails.setRoles(roles);

        return userRepo.save(userDetails);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());

        Company company = companyRepo.findById(userDetails.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        user.setCompany(company);

        if (userDetails.getPosition() != null) {
            Position position = positionRepo.findById(userDetails.getPosition().getId())
                    .orElseThrow(() -> new RuntimeException("Position not found"));
            user.setPosition(position);
        }

        if (userDetails.getRoles() != null && !userDetails.getRoles().isEmpty()) {
            Set<Role> roles = userDetails.getRoles().stream()
                    .map(r -> roleRepo.findById(r.getId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id " + r.getId())))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
