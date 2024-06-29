package com.example.MeliUrlShorter.bussines.url.service;

import com.example.MeliUrlShorter.bussines.url.exception.UrlNotFoundException;
import com.example.MeliUrlShorter.bussines.url.model.Url;
import com.example.MeliUrlShorter.bussines.url.service.mapper.IUrlMapper;
import com.example.MeliUrlShorter.bussines.url.service.urlServiceInterface.IShortMeli;
import com.example.MeliUrlShorter.persistance.IMeliPersistance;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;
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
        System.out.println(hash + " " + urlToSave.toString());
        urlToSave.setHash(hash);
        //salvamos la url
        meliUrlPersistance.save(urlToSave);
        System.out.println(urlRequestToShort + " " + hash);

        //Devolvemos la url accesible
        return urlToShort + hash;
    }


    @Override // Este metodo genera el hash mediante el algoritmo SHA-256
    public String generateHash(String url, int length) {
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
        meliUrlPersistance.findById(hash).orElseThrow(() -> new UrlNotFoundException("Url not found"));


        //Mappeamos la requet a una url
        Url urlUpdateMapped = urlMapperUpdate.toUrl(urlToUpdate);
        urlUpdateMapped.setHash(hash);


        //Actualizamos la url
        meliUrlPersistance.save(urlUpdateMapped);

        //Devolvemos la url accesible, que sera la misma ya que el hash no cambia
        return urlToShort + hash;


    }


    @Override
    public Url urlDesestructured(String urlToDestructured) throws MalformedURLException {
        //Una vez aca comenzamos con la desestructuracion
        URL url = new URL(urlToDestructured);
        String tld = url.getHost().substring(url.getHost().indexOf(".") + 1);
        String domain = url.getHost().substring(0, url.getHost().indexOf("."));

        Url urlDesestructured = new Url(
                url.getProtocol(),
                domain,
                tld,
                url.getPort() == -1 ? "" : url.getPort() + "",
                url.getPath() // en nuestro objeto se llama route
        );
        return urlDesestructured;
    }

    @Override
    public void enableShortUrl(String urlToEnable) {
//        String hash = getHashFromUrl(urlToEnable);
//        String URL = meliUrlPersistance.getUrlResolve(hash);
//        if(URL.contains(":DISABLED")){
//            urlToEnable = URL.replace(":DISABLED","");
//        }
//        meliUrlPersistance.enableUrl(hash, urlToEnable);
    }


    @Override
    public void disableShortUrl(String urlToDisable) {


    }

    @Override
    public Map<String,String > checkUrl(String urlToCheck) throws Exception {
        String hash = getHashFromUrl(urlToCheck);
        Url urlSaved = meliUrlPersistance.findById(hash).orElseThrow(() -> new UrlNotFoundException("Url not found"));

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




    @Override
    public Url getUrlResolve(String hash) throws Exception {
        System.out.println(hash);
        return meliUrlPersistance.findById(hash).orElseThrow(() -> new UrlNotFoundException("Url not found"));
    }


    String getHashFromUrl(String urlToGetHash) {
        return urlToGetHash.substring(urlToGetHash.length() - 6).trim();
    }


    //*********************** Delete Url ************************************




}
