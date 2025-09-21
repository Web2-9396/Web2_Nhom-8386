package com.example.btvn_tuan6.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Long companyId;

}
