package com.example.finalexam.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginInfoDTO {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private String departmentName;


    public LoginInfoDTO id(Integer id) {
        this.id = id;
        return this;
    }

    public LoginInfoDTO username(String username) {
        this.username = username;
        return this;
    }

    public LoginInfoDTO password(String password) {
        this.password = password;
        return this;
    }

    public LoginInfoDTO firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public LoginInfoDTO lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public LoginInfoDTO role(String role) {
        this.role = role;
        return this;
    }

    public LoginInfoDTO departmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }
}
