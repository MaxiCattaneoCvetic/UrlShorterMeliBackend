package com.example.MeliUrlShorter.bussines.service.url;

import com.example.MeliUrlShorter.bussines.model.Url;
import com.example.MeliUrlShorter.bussines.service.url.urlMapper.IUrlMapper;
import com.example.MeliUrlShorter.bussines.service.url.urlServiceInterface.IShortMeli;
import com.example.MeliUrlShorter.persistance.IMeliPersistance;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        String urlToSave = urlMapperUpdate.toUrl(urlRequestToShort).toString();


        // Generamos el hash
        var hash = shortUrl(urlToSave, 6);

        //salvamos la url
        meliUrlPersistance.saveUrl(hash, urlToSave);

        System.out.println(urlRequestToShort + " " + hash);

        //Devolvemos la url accesible
        return urlToShort + hash;
    }


    @Override // Este metodo genera el hash mediante el algoritmo SHA-256
    public String shortUrl(String url, int length) {
        var bytes = encripter.digest(url.getBytes());
        var hash = String.format("%32x", new BigInteger(1, bytes));
        return hash.substring(0, length);

    }


    //*********************** Update Url ************************************

    @Override
    public String updateUrlAttribute(RequestUrl urlToUpdate, String shortUrl) {
        //recibo la url corta y le saco el hash
        String hash = shortUrl.substring(shortUrl.length() - 6).trim();

        //Mapeamos la request a un objeto URL para nuestro sistema.
        var urlMapped = urlMapperUpdate.toUrl(urlToUpdate).toString();

        //Enviamos a actualizar la url
        meliUrlPersistance.updateUrl(hash, urlMapped);

        //Retornamos la url actualizada que sera la misma ya que el hash no cambia
        return urlToShort + hash;
    }

    @Override
    public void disableShortUrl(String urlToDisable) {
        //recibo la url a desabilitar y le saco el hash
        String hash = urlToDisable.substring(urlToDisable.length() - 6).trim();

        //Desabilitamos la url
        meliUrlPersistance.disableUrl(hash);

    }




    public String getUrlResolve(String hash) {
        System.out.println(hash);
        return meliUrlPersistance.getUrlResolve(hash);
    }


    //*********************** Delete Url ************************************




}
