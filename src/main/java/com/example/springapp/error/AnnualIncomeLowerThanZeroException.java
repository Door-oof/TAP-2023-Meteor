package com.example.springapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AnnualIncomeLowerThanZeroException extends RuntimeException{
    public AnnualIncomeLowerThanZeroException() {
        super("Annual income cannot be below 0");
    }
}
