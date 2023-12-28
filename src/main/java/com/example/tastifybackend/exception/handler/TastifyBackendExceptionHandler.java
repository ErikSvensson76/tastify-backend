package com.example.tastifybackend.exception.handler;

import com.example.tastifybackend.domain.response.ErrorResponse;
import com.example.tastifybackend.exception.EntityNotFoundException;
import com.example.tastifybackend.exception.MismatchedIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class TastifyBackendExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException exception,
            WebRequest request
    ){
        return ResponseEntity.badRequest().body(
                ErrorResponse.buildApiResponse(exception, HttpStatus.BAD_REQUEST, request)
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException exception,
            WebRequest request
    ){
        return ResponseEntity.status(404).body(
                ErrorResponse.buildApiResponse(exception, HttpStatus.NOT_FOUND, request)
        );
    }

    @ExceptionHandler(MismatchedIdentifierException.class)
    public ResponseEntity<ErrorResponse> handleMismatchedIdentifierException(
        MismatchedIdentifierException exception,
        WebRequest request
    ){
        return ResponseEntity.badRequest().body(
            ErrorResponse.buildApiResponse(exception, HttpStatus.BAD_REQUEST, request)
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(
            IllegalStateException exception,
            WebRequest request
    ){
        return ResponseEntity.badRequest().body(
                ErrorResponse.buildApiResponse(exception, HttpStatus.BAD_REQUEST, request)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception,
            WebRequest request
    ){
        return ResponseEntity.badRequest().body(
                ErrorResponse.buildApiResponse(exception, request)
        );
    }


}
