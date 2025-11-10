package com.droidchat.DroidChat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(String message){
        super(message);
    }
}
