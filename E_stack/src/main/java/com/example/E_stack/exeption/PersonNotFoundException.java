package com.example.E_stack.exeption;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(String message) {
        super(message);
    }

}