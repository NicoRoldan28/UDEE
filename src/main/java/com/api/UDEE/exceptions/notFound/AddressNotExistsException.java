package com.api.UDEE.exceptions.notFound;

public class AddressNotExistsException extends RuntimeException{
    public AddressNotExistsException(String message){
        super(message);
    }
}
