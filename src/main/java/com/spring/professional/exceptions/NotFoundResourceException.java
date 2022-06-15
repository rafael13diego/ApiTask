package com.spring.professional.exceptions;

import lombok.Data;

@Data
public class NotFoundResourceException extends RuntimeException{

    private String code;
    private String message;

    public NotFoundResourceException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
