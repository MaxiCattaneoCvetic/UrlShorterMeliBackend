package com.example.MeliUrlShorter.bussines.url.service;

import com.example.MeliUrlShorter.bussines.url.exception.URLBadRequestException;
import com.example.MeliUrlShorter.bussines.url.exception.URLNotFoundException;
import com.example.MeliUrlShorter.bussines.url.exceptionTypes.BadRequestException;
import com.example.MeliUrlShorter.bussines.url.model.Url;
import com.example.MeliUrlShorter.bussines.url.service.mapper.IUrlMapper;
import com.example.MeliUrlShorter.bussines.url.service.urlServiceInterface.IShortMeli;
import com.example.MeliUrlShorter.persistance.IMeliPersistance;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class ShortMeliService implements IShortMeli {

    private static final Logger log = LoggerFactory.getLogger(ShortMeliService.class);
    @Autowired
    final private IUrlMapper<RequestUrl> urlMapperUpdate;
    final private IMeliPersistance meliUrlPersistance;

    // SHA-256 es un algoritmo de encriptacion de 256 bits de longitud, esto me permite encriptar y desencriptar datos
    MessageDigest encripter = MessageDigest.getInstance("SHA-256");


    @Value("${meli.url}")
    private String urlToShort;

    public ShortMeliService(IUrlMapper<RequestUrl> urlMapperUpdate, IMeliPersistance meliUrlPersistance) throws NoSuchAlgorithmException {
        this.urlMapperUpdate = urlMapperUpdate;
        this.meliUrlPersistance = meliUrlPersistance;
    }


    //*********************** Create Short Url ************************************

    @Override
    public String saveUrl(RequestUrl urlRequestToShort) {

        //Mapeamos la request a un objeto URL para nuestro sistema.
        Url urlToSave = urlMapperUpdate.toUrl(urlRequestToShort);


        // Generamos el hash
        var hash = generateHash(urlToSave.toString(), 6);

        //Le seteamos el hash, para nostros el HASH es el ID
        urlToSave.setHash(hash);

        //salvamos la url
        meliUrlPersistance.save(urlToSave);
        log.info("(saveUrl) -> Url saved: {}", urlToSave);
        //Devolvemos la url accesible
        return urlToShort + hash;
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
    public Map<String,String > checkUrl(String urlToCheck) throws Exception {
        log.info("(checkUrl) -> Chequeando la existencia de la URL");
        String hash = getHashFromUrl(urlToCheck);
        Url urlSaved = meliUrlPersistance.findById(hash).orElseThrow(() -> new URLNotFoundException("Url not found"));

        //Si no hay una URL com ese hash lanzamos excepcion
        if (urlSaved == null) {
            throw new Exception("Url not found");
        }
        //Desustruramos la URL para en front ya que la recibe por partes, en el caso de no tener front podriamos pasar la url directamente y cambiarla a la que queramos
        Url url = urlDesestructured(urlSaved.toString());

        //Este Map es mas que nada para que el front pueda diferenciar y alojar cada dato en el input
        return Map.of(
                "protocol", url.protocol(),
                "domain", url.domain(),
                "tld", url.tld(),
                "port", url.port(),
                "route", url.route()
        );


    }

    //*********************** GETS ************************************

    @Override
    public Url getUrlResolve(String hash) throws Exception {
        log.info("(getUrlResolve) -> Obteniendo la URL");
        Url urlFound = meliUrlPersistance.findById(hash).orElseThrow(() -> new URLNotFoundException("Url not found"));
        if(urlFound.isActive()){
            return  urlFound;
        }else if (!urlFound.isActive()){
            throw new URLBadRequestException("Url not active, plase enable it");
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
        log.info("(enableShortUrl) -> Habilitando la URL");
        String hash = getHashFromUrl(urlToEnable);
        Url urlFoundToEnable = meliUrlPersistance.findById(hash).orElseThrow(() -> new URLNotFoundException("Url not found"));
        urlFoundToEnable.setActive(true);
        meliUrlPersistance.save(urlFoundToEnable);
    }




    @Override
    public void disableShortUrl(String urlToDisable) {
        log.info("(disableShortUrl) -> Desabilitando la URL");
        String hash = getHashFromUrl(urlToDisable);
        Url urlFoundToEnable = meliUrlPersistance.findById(hash).orElseThrow(() -> new URLNotFoundException("Url not found"));
        urlFoundToEnable.setActive(false);
        meliUrlPersistance.save(urlFoundToEnable);


    }

    //*********************** ADDITIONAL METHODS ************************************

    @Override
    public Url urlDesestructured(String urlToDestructured) throws MalformedURLException {
        log.info("(urlDesestructured) -> Desestructurando la url: {}", urlToDestructured);
        //Una vez aca comenzamos con la desestructuracion
        URL url = new URL(urlToDestructured);

        //No hay una manera de sacar el .com .ar etc, obtenemos el host y vamos hasta la posicion del . y ahi si obtenemos el tld
        int indexOfDot = url.getHost().indexOf(".");
        String tld = url.getHost().substring(indexOfDot + 1);
        String domain = url.getHost().substring(0, indexOfDot);

        Url urlDesestructured = new Url(
                url.getProtocol(),
                domain,
                tld,
                url.getPort() == -1 ? "" : url.getPort() + "", // Aca lo que pasa es que si existe el puerto nos devuelve el puerto, de lo contrario nos devuelve -1
                url.getPath() // en nuestro objeto se llama route
        );
        return urlDesestructured;
    }


}
