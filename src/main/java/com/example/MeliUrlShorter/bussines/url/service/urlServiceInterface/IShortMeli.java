package com.example.MeliUrlShorter.bussines.url.service.urlServiceInterface;

import com.example.MeliUrlShorter.bussines.url.model.Url;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;

import java.net.MalformedURLException;

import java.util.Map;

public interface IShortMeli {

    String saveUrl(RequestUrl urlRequestToShort);

    String generateHash(String url, int length);

    Url getUrlResolve(String hash) throws Exception;
    String updateUrlAttribute(RequestUrl urlToUpdate, String shortUrl) throws Exception;

    void disableShortUrl(String urlToDisable);
    Map<String,String> checkUrl(String urlToCheck) throws Exception;

    Url urlDesestructured (String urlToDestructured) throws MalformedURLException;

    void enableShortUrl(String urlToEnable);
}
