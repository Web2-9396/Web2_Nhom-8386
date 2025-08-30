package com.example.bttuan3.repository;


import com.example.bttuan3.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}