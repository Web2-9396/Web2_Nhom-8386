package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class NhomController {

    @GetMapping("/")
    public String showTeam(Model model) {
        List<String> members = Arrays.asList("Thái Văn Phúc", "Cao Nguyên Bình An", "Lê Thị Thu Thảo", "Nguyễn Lê văn Hồng Phúc");
        model.addAttribute("members", members);
        return "nhom";
    }
}
