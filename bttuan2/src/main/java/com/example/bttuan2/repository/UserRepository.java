package com.example.bttuan2.repository;

import com.example.bttuan2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> { // id của bạn là int, không phải Long
    User findByName(String name);
    User findByEmail(String email);
    User findByNameOrEmail(String name, String email);
}
