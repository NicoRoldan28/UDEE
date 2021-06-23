package com.api.UDEE.exceptions.notFound;

public class ModelNotExistsException extends RuntimeException{
    public ModelNotExistsException(String message){
        super(message);
    }
}
