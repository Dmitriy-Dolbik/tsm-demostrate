package ru.karod.tsm.exceptions;

public class InvalidRequestValuesException extends RuntimeException{
    public InvalidRequestValuesException(String message){
        super(message);
    }
}
