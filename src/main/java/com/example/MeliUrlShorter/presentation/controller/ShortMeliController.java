package com.example.MeliUrlShorter.presentation.controller;

import com.example.MeliUrlShorter.bussines.url.service.urlServiceInterface.IShortMeli;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ShortMeliController {

    @Autowired
    private IShortMeli shortMeliService;


    //------------------CREAR UNA NUEVA URL CORTA---------------------
    @PostMapping
    public String shortUrl(@RequestBody RequestUrl requestUrl) {
        //Recibo la url de la request
        return shortMeliService.saveUrl(requestUrl);
    }


    // Este endpoint sirve para actualizar los campos de la ulr
    @PutMapping()
    public String updateUrlAttribute(@RequestBody RequestUrl urlToUpdate,
                                     @RequestParam String shortUrl) throws Exception {
        return shortMeliService.updateUrlAttribute(urlToUpdate, shortUrl);
    }

    // Este endpoint sirve para obtener la URL larga mediante el URL corto.
    @GetMapping("/{hash}")
    public ResponseEntity<?> getLargeUrlFromShortUrl(@PathVariable String hash) throws Exception {
        var target = shortMeliService.getUrlResolve(hash);
        return
                ResponseEntity
                        .status(HttpStatus.MOVED_PERMANENTLY)
                        .location(URI.create(target.toString()))
                        .header(HttpHeaders.CONNECTION, "close")
                        .build();
    }

    // Este endpoint sirve para obtener la URL larga mediante el URL corto.
    @PutMapping("/disable")
    public void disableShortUrl(@RequestParam String urlToDisable) {
        shortMeliService.disableShortUrl(urlToDisable);
    }

    @PutMapping("/enable")
    public void enableShortUrl(@RequestParam String urlToEnable) {
        shortMeliService.enableShortUrl(urlToEnable);
    }

    @GetMapping("/check")
    public Map<String, String> checkIfShortUrlExists(@RequestParam String urlToCheck) throws Exception {
        return shortMeliService.checkUrl(urlToCheck);
    }


}




