package com.transporter.exceptions;

public class UserIdNotFoundException extends RuntimeException {

    public UserIdNotFoundException(String message){
        super(message);
    }
}
