package com.example.bttuan2.controllers;

import com.example.bttuan2.models.User;
import com.example.bttuan2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Trang chủ
    @GetMapping("/")
    public String showTeam() {
        return "index";
    }

    // Hiển thị danh sách người dùng
    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user_list";
    }

    // Hiển thị form thêm mới
    @GetMapping("/user-form")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }

    // Lưu user (thêm mới)
    @PostMapping("/add-user")
    public String saveUser(@ModelAttribute User user, Model model) {
        userRepository.save(user);
        model.addAttribute("message", "Người dùng đã được lưu thành công!");
        model.addAttribute("user", new User()); // reset form
        return "user_form";
    }

    // Hiển thị form sửa
    @GetMapping("/edit-user/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user_form";
        } else {
            model.addAttribute("message", "Không tìm thấy người dùng!");
            return "redirect:/users";
        }
    }

    // Cập nhật user (dùng chung form với thêm mới)
    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    // Xóa user
    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
