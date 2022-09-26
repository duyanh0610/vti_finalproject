//package com.example.finalexam.exception;
//
//import com.example.finalexam.utils.HttpUtils;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//@Component
//public class AuthenException implements AuthenticationEntryPoint, AccessDeniedHandler {
//    private final HttpUtils httpUtils;
//
//    public AuthenException(HttpUtils httpUtils) {
//        this.httpUtils = httpUtils;
//    }
//
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException authenticationException) throws IOException, ServletException {
//
//        Error error = new Error()
//                .errorCode("authentication.login.unauthenticated")
//                .errorStatus("401")
//                .errorMessage(authenticationException.getMessage());
//        ObjectWriter ow =  new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(error);
//
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(json);
//
//    }
//
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//
//    }
//
//
//}
