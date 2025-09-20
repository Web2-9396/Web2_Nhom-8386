package com.example.bttuan3.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "COMPANY")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String companyName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    @JsonManagedReference  // Serialize từ Company → Users
    private List<User> users;

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> users) { this.users = users; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
