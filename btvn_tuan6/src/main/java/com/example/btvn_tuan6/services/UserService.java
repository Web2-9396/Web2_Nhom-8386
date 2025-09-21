package com.example.btvn_tuan6.services;

import com.example.btvn_tuan6.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User userDetails);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
}
