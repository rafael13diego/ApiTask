package com.spring.professional.exceptions;

import static com.spring.professional.utils.ConstantsUtil.EXISTS_RESOURCE_ERR;

public class ExistsResourceException extends RuntimeException{

    public ExistsResourceException(String detail){
        super(EXISTS_RESOURCE_ERR + ", "+detail);
    }
}
