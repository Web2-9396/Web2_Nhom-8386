package com.example.bttuan2.controllers;

import com.example.bttuan2.models.User;
import com.example.bttuan2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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

    // Danh sách user
    @GetMapping("/user")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users); 
        return "user"; 
    }

    // Lưu user mới
    @PostMapping("/add-user")
    public String saveUser(@ModelAttribute User user, Model model) {
        userRepository.save(user);
        return "redirect:/user"; 
    }
    // Form sửa user
    @GetMapping("/edit-user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "edit_user";  
    }

    // Cập nhật user
    @PostMapping("/update-user/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        user.setId(id);
        userRepository.save(user);
        return "redirect:/user";
    }

    // Xóa user
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/user";
    }
}
