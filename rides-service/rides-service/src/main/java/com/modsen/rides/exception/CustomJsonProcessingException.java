package com.modsen.rides.exception;

public class CustomJsonProcessingException extends RuntimeException {
    public CustomJsonProcessingException(String message) {
        super(message);
    }

    public CustomJsonProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
