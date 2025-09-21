package com.example.btvn_tuan6.services;

import com.example.btvn_tuan6.dto.AuthResponse;
import com.example.btvn_tuan6.dto.LoginDTO;
import com.example.btvn_tuan6.dto.RegisterDTO;

public interface AuthService {
    AuthResponse register (RegisterDTO dto);
    AuthResponse login (LoginDTO dto);
}
