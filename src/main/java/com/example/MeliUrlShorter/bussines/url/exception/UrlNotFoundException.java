package com.example.MeliUrlShorter.bussines.url.exception;


import com.example.MeliUrlShorter.bussines.url.exceptionTypes.NotFoundException;

public class UrlNotFoundException extends NotFoundException {


    public UrlNotFoundException(String message) {
        super(message);
    }
}
