package br.com.zaix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException {

    public RequiredObjectIsNullException() {
        super("It's not allowed to persist a null object!");
    }

     public RequiredObjectIsNullException(String message) {
        super(message);
    }
}
