package com.example.MeliUrlShorter.bussines.url.exception;


import com.example.MeliUrlShorter.bussines.url.exceptionTypes.NotFoundException;

public class URLNotFoundException extends NotFoundException {


    public URLNotFoundException(String message) {
        super(message);
    }
}
