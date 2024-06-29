package com.example.MeliUrlShorter.presentation.controller.advisor;

import java.time.LocalDateTime;

/*
 Clase para el manejo de las respuestas globales, el error, el status y el path
 Esta clase ayuda a construir una respuesta mas completa y correcta a las peticiones.
* */
public class GlobalErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public GlobalErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }


}
