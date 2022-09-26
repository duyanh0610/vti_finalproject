package com.example.finalexam.service;

import com.example.finalexam.exception.CustomError;
import com.example.finalexam.exception.CustomException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImplement implements AuthenticationService{
    private final AccountService accountService;

    public AuthenticationServiceImplement(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null){
            throw new CustomException()
                        .customError(new CustomError()
                            .code("authentication.username.usernameIsNull")
                            .param(null));
        }
        return accountService.findByUsername(username)
                .map(accountDTO -> new User(
                        accountDTO.getUsername(),
                        accountDTO.getPassword(),
                        AuthorityUtils.createAuthorityList(accountDTO.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
