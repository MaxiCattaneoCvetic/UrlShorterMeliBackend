package com.example.MeliUrlShorter.presentation.controller.advisor;

import com.example.MeliUrlShorter.bussines.url.exception.URLBadRequestException;
import com.example.MeliUrlShorter.bussines.url.exception.URLNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/*
 El controller advice se encarga de manejar las excepciones globales
* */

@ControllerAdvice
public class GlobalHandlerExceptions {

    @ExceptionHandler(URLNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(URLNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(URLBadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(URLBadRequestException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}