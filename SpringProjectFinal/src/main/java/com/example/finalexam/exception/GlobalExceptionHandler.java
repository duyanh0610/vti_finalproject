package com.example.finalexam.exception;

import com.example.finalexam.utils.HttpUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final HttpUtils httpUtils;

    public GlobalExceptionHandler(HttpUtils httpUtils) {
        this.httpUtils = httpUtils;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        for (ObjectError error: ex.getBindingResult().getAllErrors()){
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        Error error = new Error()
                .errorCode("methodNotAllow")
                .errorStatus("405")
                .errorMessage("not allow"
                );

        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Error> handleCustomException(CustomException exception,WebRequest webRequest){
        String lang = webRequest.getParameter("lang");
        Error error = new Error()
                        .errorCode(exception.getCustomError().getCode())
                        .errorStatus("400")
                        .errorMessage(httpUtils
                                .populateMessage(exception, new Locale(httpUtils.getLanguage(webRequest)))
                                .getMessage()
                        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
//
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Error> handleException(CustomException exception,WebRequest webRequest){
        String lang = webRequest.getParameter("lang");
        Error error = new Error()
                .errorCode(exception.getCustomError().getCode())
                .errorStatus("400")
                .errorMessage(httpUtils
                        .populateMessage(exception, new Locale(httpUtils.getLanguage(webRequest)))
                        .getMessage()
                );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
