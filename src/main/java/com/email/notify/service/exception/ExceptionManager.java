package com.email.notify.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice()
public class ExceptionManager {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            response.put(field,message);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String,String>> dataNotFoundException(DataNotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", "true");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<Map<String,String>> emailNotValidException(EmailNotValidException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", "true");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InValidContentTypeException.class)
    public ResponseEntity<Map<String,String>> inValidContentTypeException(InValidContentTypeException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", "true");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Map<String,String>> fileNotFoundException(FileNotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("error", "true");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
