package com.example.MeliUrlShorter.bussines.url.service;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.example.MeliUrlShorter.bussines.metrics.UrlShortCustomMetrics;
import com.example.MeliUrlShorter.bussines.url.exception.URLBadRequestException;
import com.example.MeliUrlShorter.bussines.url.exception.URLNotFoundException;
import com.example.MeliUrlShorter.bussines.url.model.Url;
import com.example.MeliUrlShorter.bussines.url.service.mapper.IUrlMapper;
import com.example.MeliUrlShorter.persistance.IMeliPersistance;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


@Service
public class ShortMeliService implements IShortMeli {

    private static final Logger log = LoggerFactory.getLogger(ShortMeliService.class);
    @Autowired
    final private IUrlMapper<RequestUrl> urlMapperUpdate;
    final private IMeliPersistance meliUrlPersistance;
    private final UrlShortCustomMetrics customMetrics;

    // SHA-256 es un algoritmo de encriptacion de 256 bits de longitud, esto me permite encriptar y desencriptar datos
    MessageDigest encripter = MessageDigest.getInstance("SHA-256");

    @Value("${meli.url}")
    private String urlToShort;

    public ShortMeliService(IUrlMapper<RequestUrl> urlMapperUpdate, IMeliPersistance meliUrlPersistance, UrlShortCustomMetrics customMetrics) throws NoSuchAlgorithmException {
        this.urlMapperUpdate = urlMapperUpdate;
        this.meliUrlPersistance = meliUrlPersistance;
        this.customMetrics = customMetrics;
    }



/*
       Documentación de implementación de CACHE para reducir costos y tiempo Se implementan varias anotaciones correspondientes para manejar el cache.
       Dejo la documentacion para recordar que hace cada una.

        @Cacheable → Se utiliza para almacenera cache en el resultado de un metodo, cuando se invoca el metodo primero verifica si la respuesta la tiene en el cache,
                      de tener la respuesta en el cache la devuelve, de lo contrario la invoca y la almacena en el cache. Utiliza clave-valor para el cache.

        @CacheEvict→ Se utiliza para eliminar un elemento del cache, lo utilizo en los metodos donde la respuesta puede cambiar, entonces necesitamos los datos
                     Actualizados
*/


    //*********************** Create Short Url ************************************

    @Override
    @CacheEvict(value = "Url", allEntries = true)
    public Url saveUrl(RequestUrl urlRequestToShort) {
        customMetrics.postAndPutCount();
        //Mapeamos la request a un objeto URL para nuestro sistema.
        Url urlToSave = urlMapperUpdate.toUrl(urlRequestToShort);


        // Generamos el hash
        var hash = generateHash(urlToSave.toString(), 6);

        //Le seteamos el hash, para nostros el HASH es el ID
        urlToSave.setHash(hash);

        meliUrlPersistance.save(urlToSave);
        log.info("(saveUrl) -> Url saved: {}", urlToSave);
        //Devolvemos la url
        return urlToSave;
    }


    @Override // Este metodo genera el hash mediante el algoritmo SHA-256
    public String generateHash(String url, int length) {
        log.info("(generateHash) -> Generando Hash para la url: {}", url);
        var bytes = encripter.digest(url.getBytes());
        var hash = String.format("%32x", new BigInteger(1, bytes));
        return hash.substring(0, length);

    }


    //*********************** Update Url ************************************

    @Override
    public String updateUrlAttribute(RequestUrl urlToUpdate, String shortUrl) throws Exception {
        customMetrics.postAndPutCount();
        //recibo la url corta y le saco el hash
        String hash = getHashFromUrl(shortUrl);

        //Buscamos la URL por el hash
        log.info("(updateUrlAttribute) -> Comenzando la verificacion de existencia de la URL");
        meliUrlPersistance.findById(hash).orElseThrow(() -> new URLNotFoundException("Url not found"));


        //Mappeamos la requet a una url
        Url urlUpdateMapped = urlMapperUpdate.toUrl(urlToUpdate);
        urlUpdateMapped.setHash(hash);


        //Actualizamos la url
        meliUrlPersistance.save(urlUpdateMapped);

        log.info("(updateUrlAttribute) -> Se actualizo la url: {}", urlUpdateMapped);
        //Devolvemos la url accesible, que sera la misma ya que el hash no cambia
        return urlToShort + hash;


    }


    @Override
    public Map<String, String> checkUrl(String urlToCheck) throws Exception {
        customMetrics.getRequestCount();
        log.info("(checkUrl) -> Chequeando la existencia de la URL");
        String hash = getHashFromUrl(urlToCheck);
        Url urlSaved = meliUrlPersistance.findById(hash).orElseThrow(() -> new URLNotFoundException("Url not found"));

        //Si no hay una URL com ese hash lanzamos excepcion
        if (urlSaved == null) {
            throw new URLNotFoundException("Url not found");
        }

        //Desustruramos la URL para en front ya que la recibe por partes, en el caso de no tener front podriamos pasar la url directamente y cambiarla a la que queramos
        //Este Map es mas que nada para que el front pueda diferenciar y alojar cada dato en el input
        return urlDesestructured(urlSaved.toString());


    }

    //*********************** GETS ************************************
    @Override
    @Cacheable(value = "Url", key = "#hashiD")
    public Url findByHash(String hashiD) {

        log.info("(findByHash) -> Buscando la URL con el hash:" + hashiD);

        Url urlFound = meliUrlPersistance.findById(hashiD).orElseThrow(() -> new URLNotFoundException("Url not found"));
        customMetrics.getRequestCount();
        if (urlFound.isActive()) {
            return urlFound;
        } else if (!urlFound.isActive()) {
            throw new URLBadRequestException("Url not active, please enable it");
        }

        return null;
    }


    String getHashFromUrl(String urlToGetHash) {
        log.info("(getHashFromUrl) -> Obteniendo el hash de la URL");
        //Nuestro hash lo formamos mediante 6 numeros, estos simpre se encontraran al final de la URL corta, por lo tanto realice un metodo para sacarlos facilmente.
        return urlToGetHash.substring(urlToGetHash.length() - 6).trim();
    }


    //*********************** ADMINISTRATOR URL ************************************

    @Override
    public void enableShortUrl(String urlToEnable) {
        customMetrics.postAndPutCount();
        log.info("(enableShortUrl) -> Habilitando la URL");
        String hash = getHashFromUrl(urlToEnable);
        Url urlFoundToEnable = meliUrlPersistance.findById(hash).orElseThrow(() -> new URLNotFoundException("Url not found"));
        urlFoundToEnable.setActive(true);
        meliUrlPersistance.save(urlFoundToEnable);

    }


    @Override
//    @CacheEvict(value = "URL-CACHE-DISABLE", key = "#urlToDisable")
    public void disableShortUrl(String urlToDisable) {
        customMetrics.postAndPutCount();
        log.info("(disableShortUrl) -> Desabilitando la URL");
        String hash = getHashFromUrl(urlToDisable);
        Url urlFoundToEnable = meliUrlPersistance.findById(hash).orElseThrow(() -> new URLNotFoundException("Url not found"));
        urlFoundToEnable.setActive(false);
        meliUrlPersistance.save(urlFoundToEnable);


    }

    //*********************** ADDITIONAL METHODS ************************************

    @Override
    public Map<String, String> urlDesestructured(String urlToDestructured) throws MalformedURLException {
        log.info("(urlDesestructured) -> Desestructurando la url: {}", urlToDestructured);
        //Una vez aca comenzamos con la desestructuracion
        URL url = new URL(urlToDestructured);

        //No hay una manera de sacar el .com .ar etc, obtenemos el host y vamos hasta la posicion del . y ahi si obtenemos el tld
        int indexOfDot = url.getHost().indexOf(".");
        String tld = url.getHost().substring(indexOfDot + 1);
        String domain = url.getHost().substring(0, indexOfDot);

        Map<String, String> urlDesestructured = Map.of(
                "protocol", url.getProtocol(),
                "domain", domain,
                "tld", tld,
                "port", url.getPort() == -1 ? "" : url.getPort() + "",
                "route", url.getPath() // en nuestro objeto se llama route
        );


        return urlDesestructured;
    }


}
