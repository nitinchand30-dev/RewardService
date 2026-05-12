package com.telusAssignment.rewards.exception;

import com.telusAssignment.rewards.dto.ApiResponse;
import com.telusAssignment.rewards.dto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Global Exception Handler for the application.
 * This class centralizes exception handling across all controllers.
 * It provides consistent error responses with proper HTTP status codes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponseDto>> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {

        logger.warn("Resource not found: {}", ex.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", "")
        );

        ApiResponse<ErrorResponseDto> response = new ApiResponse<>(
                false,
                "Request failed",
                "Resource Not Found",
                HttpStatus.NOT_FOUND.value()
        );
        response.setData(errorResponse);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle InvalidRequestException
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse<ErrorResponseDto>> handleInvalidRequestException(
            InvalidRequestException ex, WebRequest request) {

        logger.warn("Invalid request: {}", ex.getMessage());

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Request",
                ex.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", "")
        );

        ApiResponse<ErrorResponseDto> response = new ApiResponse<>(
                false,
                "Request failed",
                "Invalid Request",
                HttpStatus.BAD_REQUEST.value()
        );
        response.setData(errorResponse);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle DatabaseException
     */
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiResponse<ErrorResponseDto>> handleDatabaseException(
            DatabaseException ex, WebRequest request) {

        logger.error("Database error: {}", ex.getMessage(), ex);

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Database Error",
                "An error occurred while processing your request. Please try again later.",
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", "")
        );

        ApiResponse<ErrorResponseDto> response = new ApiResponse<>(
                false,
                "Request failed",
                "Database Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        response.setData(errorResponse);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponseDto>> handleGlobalException(
            Exception ex, WebRequest request) {

        logger.error("Unexpected error: {}", ex.getMessage(), ex);

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please contact support if the problem persists.",
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", "")
        );

        ApiResponse<ErrorResponseDto> response = new ApiResponse<>(
                false,
                "Request failed",
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        response.setData(errorResponse);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
