package com.example.MeliUrlShorter.bussines.url.exceptionTypes;

/*
 Clase padre de las excepciones de Bad request
 */

public class BadRequestException extends org.apache.coyote.BadRequestException {
    public BadRequestException(String message) {
        super(message);
    }
}
