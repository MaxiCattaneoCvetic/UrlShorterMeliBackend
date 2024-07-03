package com.example.MeliUrlShorter.bussines.url.service;

import com.example.MeliUrlShorter.bussines.url.model.Url;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;

import java.net.MalformedURLException;

import java.util.List;
import java.util.Map;


public interface IShortMeli {

    Url saveUrl(RequestUrl urlRequestToShort);

    String generateHash(String url, int length);

    Url findByHash(String hash) throws Exception;
    String updateUrlAttribute(RequestUrl urlToUpdate, String shortUrl) throws Exception;

    void disableShortUrl(String urlToDisable);
    Map<String,String> checkUrl(String urlToCheck) throws Exception;

    Map<String, String> urlDesestructured (String urlToDestructured) throws MalformedURLException;

    void enableShortUrl(String urlToEnable);


}
