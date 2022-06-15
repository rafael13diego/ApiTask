package com.spring.professional.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
//@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO{

    private String code;
    private String message;
    private Map<String,String> errors;


    public ErrorDTO(String code, String message) {

        this.code = code;
        this.message = message;
    }
}
