package com.example.baitaptuan22.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.baitaptuan22.models.User;

import ch.qos.logback.core.model.Model;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping("/")
    public String getUsers(Model model) {
        List<User> users;
        model.addAttribute("title", "User List");
        model.a ddAttribute("users", users);
        return "index";
    }
    
}
