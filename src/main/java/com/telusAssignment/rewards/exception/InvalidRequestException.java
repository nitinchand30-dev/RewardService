package com.telusAssignment.rewards.exception;

/**
 * Exception thrown when an invalid request is made.
 */
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
