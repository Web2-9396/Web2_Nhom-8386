package com.example.member.controller;

import com.example.member.models.User;
import com.example.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Danh sách người dùng
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "home"; 
    }

    // Chi tiết người dùng
    @GetMapping("/user/{id}")
    public String userDetail(@PathVariable int id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "detail_user";
    }

    // Them người dùng 
    @GetMapping("/user/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new_user"; 
    }
    // Lưu người dùng mới
    @PostMapping("/users")
    public String saveUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users"; 
    }
}
