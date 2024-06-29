package com.example.MeliUrlShorter.bussines.url.exception;

import com.example.MeliUrlShorter.bussines.url.exceptionTypes.BadRequestException;

public class URLBadRequestException extends BadRequestException {

    public URLBadRequestException(String message) {
        super(message);
    }
}
