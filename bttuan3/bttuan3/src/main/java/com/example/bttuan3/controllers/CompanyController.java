package com.example.bttuan3.controllers;

import com.example.bttuan3.models.Company;
import com.example.bttuan3.models.User;
import com.example.bttuan3.repository.CompanyRepository;
import com.example.bttuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    @Autowired private CompanyRepository companyRepo;
    @Autowired private UserRepository userDemoRepository;

    // Hiển thị danh sách công ty
    @GetMapping
    public String getAllCompanies(Model model) {
        List<Company> companies = companyRepo.findAll();
        model.addAttribute("companies", companies);
        model.addAttribute("company", new Company()); // để bind form
        return "companies"; // trả về companies.html
    }

    // Thêm công ty
    @PostMapping
    public String addCompany(@ModelAttribute Company company) {
        companyRepo.save(company);
        return "redirect:/companies";
    }

    // Xóa công ty
    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable int id) {
        companyRepo.deleteById((long) id);
        return "redirect:/companies";
    }

    // Xem nhân viên trong công ty
    @GetMapping("/{companyId}/users")
    public String getUsersByCompany(@PathVariable Long companyId, Model model) {
        Company company = companyRepo.findById(companyId).orElse(null);
        if (company == null) {
            model.addAttribute("error", "Không tìm thấy công ty");
            return "error"; // bạn có thể làm 1 trang error.html
        }
        model.addAttribute("company", company);
        model.addAttribute("users", company.getUsers());
        return "company-users"; // sẽ render danh sách user
    }

}
