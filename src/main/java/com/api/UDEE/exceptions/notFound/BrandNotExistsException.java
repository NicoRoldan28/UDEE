package com.api.UDEE.exceptions.notFound;

public class BrandNotExistsException extends RuntimeException{
    public BrandNotExistsException(String message){
        super(message);
    }
}
