package com.example.finalexam.controller;

import com.example.finalexam.entity.dto.AccountDTO;
import com.example.finalexam.entity.dto.LoginInfoDTO;
import com.example.finalexam.entity.dto.LoginRequest;
import com.example.finalexam.entity.dto.RegisterRequest;
import com.example.finalexam.service.AccountService;
import com.example.finalexam.service.AuthenticationService;
import com.example.finalexam.service.LoginService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationController {
    private final ModelMapper modelMapper;
    private final AccountService accountService;
    private final LoginService loginService;

    public AuthenticationController(ModelMapper modelMapper, AccountService accountService, LoginService loginService) {
        this.modelMapper = modelMapper;
        this.accountService = accountService;
        this.loginService = loginService;

    }

//    @GetMapping("/login")
//    public ResponseEntity<LoginInfoDTO> login(Principal principal){
//        String userName =  principal.getName();
//        LoginInfoDTO loginInfoDTO = modelMapper.map(accountService.findByUsername(userName),LoginInfoDTO.class);
//        return ResponseEntity.ok().body(loginInfoDTO);
//    }

    @PostMapping("/register")
    ResponseEntity<Optional<AccountDTO>> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok().body(loginService.register(registerRequest));
    }

    @PostMapping("/login")
    ResponseEntity<Optional<LoginInfoDTO>> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(loginService.login(loginRequest));
    }
}
