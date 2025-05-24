package com.example.bttuan2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.bttuan2.models.User;
import com.example.bttuan2.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showTeam() {
        return "index";
    }

    @GetMapping("/user-form")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping("/add-user")
    public String saveUser(@ModelAttribute User user, Model model) {
        userRepository.save(user);
        model.addAttribute("message", "Người dùng đã được lưu thành công!");
        return "user_form";
    }
}