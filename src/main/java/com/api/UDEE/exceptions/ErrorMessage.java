package com.api.UDEE.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorMessage extends Exception{
    String route;
    String method;
    HttpStatus httpStatus;
}
