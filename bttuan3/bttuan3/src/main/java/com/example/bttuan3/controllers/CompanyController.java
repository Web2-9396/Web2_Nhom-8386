package com.example.bttuan3.controllers;

import com.example.bttuan3.models.Company;
import com.example.bttuan3.models.User;
import com.example.bttuan3.repository.CompanyRepository;
import com.example.bttuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired private CompanyRepository companyRepository;
    @Autowired private UserRepository userDemoRepository;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable int id) {
        companyRepository.deleteById(id);
    }

    @PostMapping("/{companyId}/users")
    public User addUserToCompany(@PathVariable int companyId, @RequestBody User userDemo) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            userDemo.setCompany(company);
            return userDemoRepository.save(userDemo);
        }
        return null;
    }

    @GetMapping("/{companyId}/users")
    public List<User> getUsersByCompany(@PathVariable int companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        return (company != null) ? company.getUsers() : null;
    }
}
