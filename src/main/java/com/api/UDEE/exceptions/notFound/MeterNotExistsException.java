package com.api.UDEE.exceptions.notFound;

public class MeterNotExistsException extends RuntimeException{
    public MeterNotExistsException(String message){
        super(message);
    }
}
