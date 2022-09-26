package com.example.finalexam.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomException extends RuntimeException implements ICommonException{
   private CustomError customError;

    public CustomException customError(CustomError customError) {
        this.customError = customError;
        return this;
    }


}
