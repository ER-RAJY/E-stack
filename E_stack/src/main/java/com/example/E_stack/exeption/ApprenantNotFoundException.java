package com.example.E_stack.exeption;

public class ApprenantNotFoundException extends RuntimeException {
    public ApprenantNotFoundException(String message) {
        super(message);
    }
}
