package org.spring.mvc.client;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BadRequestException extends RuntimeException{
    private final List<String> errors;
    public BadRequestException(List<String> errors){
        this.errors = errors;
    }
    public BadRequestException( String message,List<String> errors){
        super(message);
        this.errors = errors;
    }
    public BadRequestException(String message, Throwable cause, List<String> errors){
        super(message, cause);
        this.errors = errors;
    }

}
