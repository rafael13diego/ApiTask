package com.spring.professional.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class ErrorMessage {

    private final String error;
    private final String message;

    ErrorMessage(Exception exception) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
    }

}