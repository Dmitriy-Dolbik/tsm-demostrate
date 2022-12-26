package ru.karod.tsm.exceptions;

public class AuthException extends RuntimeException{
    public AuthException(String msg){
        super(msg);
    }
}
