package com.spring.professional.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BusinessException extends RuntimeException{
    private String code;
    //private HttpStatus status;
    private String message;

    public BusinessException(String code, String message/*,HttpStatus status*/) {
        super(message);
        this.code = code;
        this.message = message;
        //this.status = status;
    }
}
