package com.ppr.java.effectivemobileproject.model.exceptions;

public class NotUniqueEmailException extends RuntimeException{
    public NotUniqueEmailException(String message){
        super(message);
    }

}