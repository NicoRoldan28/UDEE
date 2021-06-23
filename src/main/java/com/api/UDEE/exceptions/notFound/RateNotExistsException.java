package com.api.UDEE.exceptions.notFound;

public class RateNotExistsException extends RuntimeException {
    public RateNotExistsException(String message){
        super(message);
    }
}
