package com.example.MeliUrlShorter.presentation.controller;

import com.example.MeliUrlShorter.bussines.service.url.urlServiceInterface.IShortMeli;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ShortMeliController {

    @Autowired
    private IShortMeli shortMeliService;


    // Este endpoint sirve para crear un nuevo url corto
    @PostMapping
    public String shortUrl(@RequestBody RequestUrl requestUrl) {
        //Recibo la url de la request
        return shortMeliService.saveUrl(requestUrl);
    }


    // Este endpoint sirve para actualizar los campos de la ulr
    @PutMapping()
    public String updateUrlAttribute(@RequestBody RequestUrl urlToUpdate,
                                     @RequestParam String shortUrl) throws Exception {
        System.out.println("urlToUpdat" + urlToUpdate);
        System.out.println("request" + shortUrl);
        return shortMeliService.updateUrlAttribute(urlToUpdate, shortUrl);
    }

    // Este endpoint sirve para obtener la URL larga mediante el URL corto.
    @GetMapping("/{hash}")
    public ResponseEntity<?> resolve(@PathVariable String hash) {
        var target = shortMeliService.getUrlResolve(hash);
        return
                ResponseEntity
                        .status(HttpStatus.MOVED_PERMANENTLY)
                        .location(URI.create(target))
                        .header(HttpHeaders.CONNECTION, "close")
                        .build();
    }

    // Este endpoint sirve para obtener la URL larga mediante el URL corto.
    @PutMapping("/disable")
    public void disableUrl(@RequestParam String urlToDisable) {
        shortMeliService.disableShortUrl(urlToDisable);
    }

    @GetMapping("/check")
    public Map<String,String> checkUrl(@RequestParam String urlToCheck) throws Exception {
       return shortMeliService.checkUrl(urlToCheck);
    }





}




