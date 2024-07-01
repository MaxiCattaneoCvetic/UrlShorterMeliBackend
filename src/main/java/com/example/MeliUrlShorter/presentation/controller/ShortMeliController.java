package com.example.MeliUrlShorter.presentation.controller;

import com.example.MeliUrlShorter.bussines.url.model.Url;
import com.example.MeliUrlShorter.bussines.url.service.IShortMeli;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ShortMeliController {

    @Autowired
    private IShortMeli shortMeliService;

    @Value("${meli.url}")
    private String urlToShort;


    //------------------CREAR UNA NUEVA URL CORTA---------------------
    @Operation(
            summary = "Obtener una URL corta",
            description = "Devuelve una URL corta para una URL larga"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestUrl.class, required = true, name = "RequestUrl"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    public String shortUrl(@RequestBody RequestUrl requestUrl) {
        //Recibo la url de la request
        Url url = shortMeliService.saveUrl(requestUrl);
        return urlToShort+url.hash();
    }


    // Este endpoint sirve para actualizar los campos de la ulr
    @Operation(
            summary = "Actualizar una URL larga",
            description = "Actualizar los componentes de una URL larga a partir de su URL corta"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestUrl.class, required = true, name = "RequestUrl"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping()
    public String updateUrlAttribute(@RequestBody RequestUrl urlToUpdate,
                                     @Parameter(description = "Url corta que deseas actualizar", required = true) @RequestParam String shortUrl) throws Exception {
        return shortMeliService.updateUrlAttribute(urlToUpdate, shortUrl);
    }

    // Este endpoint sirve para obtener la URL larga mediante el URL corto.
    @Operation(
            summary = "Obtener una URL larga a partir de una URL corta",
            description = "Obtene la url almacenada a partir de su url corta"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/{hash}")
    public ResponseEntity<?> getLargeUrlFromShortUrl(@Parameter(description = "url del servidor y hash correspndiente", required = true)@PathVariable String hash) throws Exception {
        Url finalUrl = shortMeliService.findByHash(hash);

        return
                ResponseEntity
                        .status(HttpStatus.MOVED_PERMANENTLY)
                        .location(URI.create(finalUrl.toString()))
                        .header(HttpHeaders.CONNECTION, "close")
                        .build();
    }

    // Este endpoint sirve para obtener la URL larga mediante el URL corto.
    @Operation(
            summary = "Deshabilitar una URL",
            description = "Deshabilitar una URL hasta que la reactives nuevamente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/disable")
    public void disableShortUrl(@Parameter(description = "url a deshabilitar", required = true)@RequestParam String urlToDisable) {
        shortMeliService.disableShortUrl(urlToDisable);
    }


    @Operation(
            summary = "Rehabilitar una URL",
            description = "Rehabilitar una URL "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PutMapping("/enable")
    public void enableShortUrl(@Parameter(description = "url a habilitar", required = true)@RequestParam String urlToEnable) {
        shortMeliService.enableShortUrl(urlToEnable);
    }

    @Operation(
            summary = "Verifica la existencia de una URL en la base de datos",
            description = "Verificamos si existe la url en la base de datos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/check")
    public Map<String, String> checkIfShortUrlExists(@Parameter(description = "url a verificar", required = true)@RequestParam String urlToCheck) throws Exception {
        return shortMeliService.checkUrl(urlToCheck);
    }



    @Operation(
            summary = "Verifica la existencia de una URL en la base de datos",
            description = "Verificamos si existe la url en la base de datos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/all")
    public List<Url> getAllUrls() {
        return shortMeliService.getAllUrls();
    }


}




