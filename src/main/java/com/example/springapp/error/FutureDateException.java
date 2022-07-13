package com.example.springapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FutureDateException extends RuntimeException {
    public FutureDateException(LocalDate date) {
        super("Birth date:" + date.toString() + " cannot be in the future");
    }
}
