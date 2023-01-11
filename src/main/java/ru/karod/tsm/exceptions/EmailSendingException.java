package ru.karod.tsm.exceptions;

public class EmailSendingException extends RuntimeException
{
    public EmailSendingException(String message){
        super(message);
    }
}
