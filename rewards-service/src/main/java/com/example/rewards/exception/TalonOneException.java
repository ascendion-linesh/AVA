package com.example.rewards.exception;

/**
 * Custom exception for Talon.One API integration errors.
 */
public class TalonOneException extends RuntimeException {
    public TalonOneException(String message) {
        super(message);
    }

    public TalonOneException(String message, Throwable cause) {
        super(message, cause);
    }
}
