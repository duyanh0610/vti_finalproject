package com.example.finalexam.utils;

import com.example.finalexam.exception.CustomError;
import com.example.finalexam.exception.ICommonException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;
import java.util.Map;

@Component
public class HttpUtils {
    private final MessageSource messageSource;

    public HttpUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public CustomError populateMessage(ICommonException ex, Locale locale) {
        if (locale == null) {
            locale = new Locale("vi", "VI");
        }
        if (ex != null) {
            CustomError customError = ex.getCustomError();

            String message = customError.getMessage();
            String code = customError.getCode();
            Object params = customError.getParam();

            if (message == null || message.isEmpty()) {
                String defaultMessage = messageSource
                        .getMessage(code, new Object[]{params},
                                "defaultMessage", locale);
                customError.message(
                        messageSource
                                .getMessage(code, new Object[]{params},
                                        defaultMessage, locale));
            }
            return customError;
        } else {
            try {
                throw new Exception();
            } catch (Exception exception) {
                System.out.println("error!!!");
            }
        }
        return new CustomError();
    }

    public String getLanguage(WebRequest webRequest) {
        return  webRequest.getHeader("lang") != null ? webRequest.getHeader("lang") : "en";
    };

    public String getLanguage(Map<String, String> headers) {
        return  headers.get("lang") != null ? headers.get("lang") : "en";
    };
}
