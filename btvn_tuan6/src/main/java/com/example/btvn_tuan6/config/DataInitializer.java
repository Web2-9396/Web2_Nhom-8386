package com.example.btvn_tuan6.config;

import com.example.btvn_tuan6.models.Role;
import com.example.btvn_tuan6.models.User;
import com.example.btvn_tuan6.repository.RoleRepository;
import com.example.btvn_tuan6.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepo.findByRoleName("ADMIN").isEmpty()) {
            roleRepo.save(new Role("ADMIN"));
        }
        if (roleRepo.findByRoleName("USER").isEmpty()) {
            roleRepo.save(new Role("USER"));
        }

// create default admin if not exists
        if (userRepo.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setFirstName("Admin");
            admin.setLastName("System");
            admin.setPassword(passwordEncoder.encode("admin123"));
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepo.findByRoleName("ADMIN").orElseThrow();
            roles.add(adminRole);
            admin.setRoles(roles);
            userRepo.save(admin);
        }
    }
}
