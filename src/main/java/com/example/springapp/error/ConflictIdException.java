package com.example.springapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictIdException extends  RuntimeException{
    public ConflictIdException(Integer id, String member) {
        super(member + " with id:" + id + " already exist");
    }
}
