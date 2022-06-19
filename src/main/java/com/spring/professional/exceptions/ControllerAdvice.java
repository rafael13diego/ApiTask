package com.spring.professional.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            ExistsResourceException.class
    })
    @ResponseBody
    public ErrorMessage existsResource(Exception exception) {
        return new ErrorMessage(exception);
    }

    /*@ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Object> businessHandlerException(BusinessException ex, HttpStatus status){
        //ErrorDTO error = new ErrorDTO(ex.getCode(),ex.getMessage());
        ErrorDTO error = ErrorDTO.builder().code("1511").message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
    @ExceptionHandler(value = NotFoundResourceException.class)
    public ResponseEntity<ErrorNFRDTO> resourceDontExistHandlerException(NotFoundResourceException ex){
        ErrorNFRDTO error = ErrorNFRDTO.builder().code(ex.getCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorNFRDTO> businessErrorException(BusinessException ex){
        ErrorNFRDTO error = ErrorNFRDTO.builder().code(ex.getCode()).message(ex.getMessage()).build();
        log.info("ErrorHandler FoundResource");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((e) -> {
            String fieldName = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            errors.put(fieldName,message);
        });
        String code = "ER-400";
        String message = "Error with request values";
        ErrorDTO errorDTO = new ErrorDTO(code, message, errors);

        return new ResponseEntity<Object>(errorDTO,HttpStatus.BAD_REQUEST);
         }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            Exception.class
    })
    @ResponseBody
    public ErrorMessage exception(Exception exception) { // The error must be corrected
        exception.printStackTrace();
        return new ErrorMessage(exception);
    }
}



/*

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
 */