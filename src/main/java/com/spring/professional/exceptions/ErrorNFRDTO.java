package com.spring.professional.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorNFRDTO {

    private String code;
    private String message;
}
