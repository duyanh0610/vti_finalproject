package com.example.finalexam.config;

import com.example.finalexam.common.Constants;
import com.example.finalexam.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationService authenticationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(AuthenticationService authenticationService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationService = authenticationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET).permitAll()
//                .antMatchers(HttpMethod.GET,"/api/v1/accounts/**").permitAll()
//                .antMatchers(HttpMethod.DELETE,"/api/v1/departments/**").permitAll()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/accounts/**").hasAnyAuthority(Constants.ROLE.ADMIN)
                .antMatchers("/api/v1/departments/**").hasAnyAuthority(Constants.ROLE.ADMIN)
//                .antMatchers("/api/v1/accounts/**").hasAnyAuthority(Constants.ROLE.MANAGER)
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
//    @Bean
//    public CorsConfigurationSource corsConfigure(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration
//                .setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","HEAD"));
//        corsConfiguration
//                .applyPermitDefaultValues();
//
//        UrlBasedCorsConfigurationSource source =  new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("*/**",corsConfiguration);
//        return  source;
//    }


}
