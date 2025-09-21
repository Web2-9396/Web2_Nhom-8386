package com.example.btvn_tuan6.services.impl;

import com.example.btvn_tuan6.config.JwtTokenProvider;
import com.example.btvn_tuan6.dto.AuthResponse;
import com.example.btvn_tuan6.dto.LoginDTO;
import com.example.btvn_tuan6.dto.RegisterDTO;
import com.example.btvn_tuan6.models.Company;
import com.example.btvn_tuan6.models.User;
import com.example.btvn_tuan6.repository.UserRepository;
import com.example.btvn_tuan6.services.AuthService;
import com.example.btvn_tuan6.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepo,
                           UserService userService,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //  Đăng ký
    @Override
    public AuthResponse register(RegisterDTO dto) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        User newUser = new User();
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setFirstName(dto.getFirstName());
        newUser.setLastName(dto.getLastName());

        // Nếu có companyId thì gán
        if (dto.getCompanyId() != null) {
            Company company = new Company();
            company.setId(dto.getCompanyId());
            newUser.setCompany(company);
        }
        newUser.setPosition(null);

        User savedUser = userService.createUser(newUser);


        return new AuthResponse(null,
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName());

    }

    // Đăng nhập
    @Override
    public AuthResponse login(LoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        String token = jwtTokenProvider.generateToken(authentication.getName());

        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        return new AuthResponse(token,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName());
    }
}
