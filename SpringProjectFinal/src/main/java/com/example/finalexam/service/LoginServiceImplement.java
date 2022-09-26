package com.example.finalexam.service;

import com.example.finalexam.common.Constants;
import com.example.finalexam.entity.Account;
import com.example.finalexam.entity.dto.AccountDTO;
import com.example.finalexam.entity.dto.LoginInfoDTO;
import com.example.finalexam.entity.dto.LoginRequest;
import com.example.finalexam.entity.dto.RegisterRequest;
import com.example.finalexam.exception.CustomError;
import com.example.finalexam.exception.CustomException;
import com.example.finalexam.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class LoginServiceImplement implements LoginService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginServiceImplement(
            AccountService accountService,
            AccountRepository accountRepository, AuthenticationManager authenticationManager,
            ModelMapper modelMapper,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<LoginInfoDTO> login(LoginRequest loginRequest)  {
        validateLogin(loginRequest);

        if(!(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        ).isAuthenticated())){
            throw  new CustomException()
                        .customError(new CustomError()
                                .code("authentication.login.unauthenticated")
                        );
        }

        return accountService.findByUsername(loginRequest.getUsername())
                .map(accountDTO -> modelMapper.map(accountDTO, LoginInfoDTO.class))
                .map(loginResponse -> loginResponse.password(null));
    }
    @Override
    public Optional<AccountDTO> register(RegisterRequest registerRequest) {
        validateRegister(registerRequest);

        Account account = new Account()
                .username(registerRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(registerRequest.getPassword()))
                .lastName(registerRequest.getLastName())
                .firstName(registerRequest.getFirstName())
                .role(Constants.ROLE.EMPLOYEE);

        return Optional.of(modelMapper.map(accountRepository.save(account), AccountDTO.class));
    }


    private void validateRegister(RegisterRequest registerRequest) {
        validateUsernamePassword(registerRequest.getUsername(), registerRequest.getPassword());
    }

    private void validateLogin(LoginRequest loginRequest) {
        validateUsernamePassword(loginRequest.getUsername(), loginRequest.getPassword());
    }

    private void validateUsernamePassword(String username, String password) {
        if (username == null || username.equals("")) {
            throw new CustomException().customError(
                    new CustomError().code("authentication.username.usernameIsNotValid")
                            .param(Collections.singleton(username))
            );
        }

        if (password == null || password.equals("")) {
            throw new CustomException().customError(
                    new CustomError().code("authentication.password.passwordIsNotValid")
                            .param(Collections.singleton(username))
            );
        }
    }
}
