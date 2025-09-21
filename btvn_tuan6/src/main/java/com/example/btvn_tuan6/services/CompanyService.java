package com.example.btvn_tuan6.services;

import com.example.btvn_tuan6.models.Company;
import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    Company createCompany(Company company);
    Company updateCompany(Long id, Company companyDetails);
    void deleteCompany(Long id);
}
