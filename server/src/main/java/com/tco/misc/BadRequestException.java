package com.tco.misc;

public class BadRequestException extends Exception {

    // Default constructor
    public BadRequestException() {
        super();
    }

    // Constructor that accepts a message
    public BadRequestException(String message) {
        super(message);
    }
}
