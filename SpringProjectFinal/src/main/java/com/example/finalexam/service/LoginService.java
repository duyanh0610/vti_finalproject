package com.example.finalexam.service;

import com.example.finalexam.entity.dto.AccountDTO;
import com.example.finalexam.entity.dto.LoginInfoDTO;
import com.example.finalexam.entity.dto.LoginRequest;
import com.example.finalexam.entity.dto.RegisterRequest;

import java.util.Optional;

public interface LoginService {
    Optional<LoginInfoDTO> login(LoginRequest loginRequest);

    Optional<AccountDTO> register(RegisterRequest registerRequest);
}
