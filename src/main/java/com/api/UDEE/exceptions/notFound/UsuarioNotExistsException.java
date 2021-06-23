package com.api.UDEE.exceptions.notFound;

public class UsuarioNotExistsException extends RuntimeException{
    public UsuarioNotExistsException(String message){
        super(message);
    }
}
