package com.api.UDEE.exceptions.notFound;

public class BillNotExistsException extends RuntimeException{

    public BillNotExistsException(String message){
        super(message);
    }
}
