package com.telusAssignment.rewards.dto;

import java.time.LocalDateTime;

/**
 * DTO for Error Response.
 * Provides detailed error information to the client.
 */
public class ErrorResponseDto {

    private int statusCode;
    private String errorMessage;
    private String errorDescription;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponseDto() {
    }

    public ErrorResponseDto(int statusCode, String errorMessage, String errorDescription,
                           LocalDateTime timestamp, String path) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.errorDescription = errorDescription;
        this.timestamp = timestamp;
        this.path = path;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
