package com.example.MeliUrlShorter.bussines.url.exceptionTypes;

/*
 Clase padre de las excepciones de not found
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

}
