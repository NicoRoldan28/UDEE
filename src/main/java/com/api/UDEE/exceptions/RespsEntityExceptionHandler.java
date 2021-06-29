package com.api.UDEE.exceptions;

import com.api.UDEE.dto.MessageDto;
import com.api.UDEE.exceptions.notFound.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RespsEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AddressNotExistsException.class, BillNotExistsException.class, BrandNotExistsException.class,
            MeterNotExistsException.class, ModelNotExistsException.class,RateNotExistsException.class,UsuarioNotExistsException.class,AddressNotExistsByUser.class})
    public ResponseEntity<Object> handleNotFound(RuntimeException ex){
        return new ResponseEntity<>(new MessageDto(ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
    
    
}
