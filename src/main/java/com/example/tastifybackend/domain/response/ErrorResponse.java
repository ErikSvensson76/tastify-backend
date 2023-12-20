package com.example.tastifybackend.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse implements Serializable {
    private String apiPath;
    private String status;
    private Integer code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    private LocalDateTime timeStamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, List<String>> validationErrors;

    ErrorResponse(){}

    public static ErrorResponse buildApiResponse(
        Exception exception,
        HttpStatus status,
        WebRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setApiPath(request.getDescription(false));
        errorResponse.setStatus(status.name());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setCode(status.value());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return errorResponse;
    }

    public static ErrorResponse buildApiResponse(
        MethodArgumentNotValidException exception,
        WebRequest request
    ){
        List<String> uniqueFields = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField)
                .distinct()
                .toList();

        Map<String, List<String>> map = new HashMap<>();

        for(String field : uniqueFields){
            List<String> list = new ArrayList<>();
            for(FieldError fieldError : exception.getFieldErrors(field)){
                list.add(fieldError.getDefaultMessage());
            }
            map.put(field, list);
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setValidationErrors(map);;
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.name());
        errorResponse.setApiPath(request.getDescription(false));
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimeStamp(LocalDateTime.now());
        return errorResponse;
    }
}
