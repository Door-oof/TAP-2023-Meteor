package com.example.springapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class HouseholdNotFoundException extends RuntimeException {
    public HouseholdNotFoundException(Integer id) {
        super("Household with id:" + id + " does not exist");
    }
}
