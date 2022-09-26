package com.example.finalexam.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    public RegisterRequest username(String username) {
        this.username = username;
        return this;
    }

    public RegisterRequest password(String password) {
        this.password = password;
        return this;
    }

    public RegisterRequest firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public RegisterRequest lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}