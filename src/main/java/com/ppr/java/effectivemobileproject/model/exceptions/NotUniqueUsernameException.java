package com.ppr.java.effectivemobileproject.model.exceptions;

public class NotUniqueUsernameException extends RuntimeException{
    public NotUniqueUsernameException(String message){
        super(message);
    }

}
