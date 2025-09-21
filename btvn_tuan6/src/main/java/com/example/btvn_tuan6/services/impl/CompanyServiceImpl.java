package com.example.btvn_tuan6.services.impl;

import com.example.btvn_tuan6.models.Company;
import com.example.btvn_tuan6.repository.CompanyRepository;
import com.example.btvn_tuan6.services.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepo;

    public CompanyServiceImpl(CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepo.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepo.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        company.setCompanyName(companyDetails.getCompanyName());
        company.setUsers(companyDetails.getUsers());
        return companyRepo.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepo.deleteById(id);
    }
}
