package ru.karod.tsm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestValuesException extends RuntimeException{
    public InvalidRequestValuesException(String message){
        super(message);
    }
}
