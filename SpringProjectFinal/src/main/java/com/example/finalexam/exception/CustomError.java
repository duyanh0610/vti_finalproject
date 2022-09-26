package com.example.finalexam.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomError {
    private String code;
    private String message;
    private Object param;

    public CustomError code(String code) {
        this.code = code;
        return this;
    }
    public CustomError message(String message) {
        this.message = message;
        return this;
    }
    public CustomError param(Object param) {
        this.param = param;
        return this;
    }

}
