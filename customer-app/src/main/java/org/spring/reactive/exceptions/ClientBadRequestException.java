package org.spring.reactive.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ClientBadRequestException extends RuntimeException{
    private final List<String> errors;

    public ClientBadRequestException(Throwable cause, List<String> errors) {
        super(cause);
        this.errors = errors;
    }
}
