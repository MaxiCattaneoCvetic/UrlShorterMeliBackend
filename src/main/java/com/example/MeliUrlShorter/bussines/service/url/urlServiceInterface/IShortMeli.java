package com.example.MeliUrlShorter.bussines.service.url.urlServiceInterface;

import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public interface IShortMeli {

    String saveUrl(RequestUrl urlRequestToShort);

    String shortUrl(String url, int length);

    String getUrlResolve(String hash);
    String updateUrlAttribute(RequestUrl urlToUpdate, String shortUrl) throws Exception;

    void disableShortUrl(String urlToDisable);
    Map<String,String> checkUrl(String urlToCheck) throws Exception;
}
