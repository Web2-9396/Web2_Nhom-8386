package com.example.bttuan2.repository;
import com.example.bttuan2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPhone(String phone);
    User findByUsernameOrEmailOrPhone(String username, String email, String phone);
}
