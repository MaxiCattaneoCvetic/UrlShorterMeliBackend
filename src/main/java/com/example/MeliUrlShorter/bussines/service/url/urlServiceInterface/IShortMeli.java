package com.example.MeliUrlShorter.bussines.service.url.urlServiceInterface;

import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;

public interface IShortMeli {

    String saveUrl(RequestUrl urlRequestToShort);

    String shortUrl(String url, int length);

    String getUrlResolve(String hash);
    String updateUrlAttribute(RequestUrl urlToUpdate, String shortUrl);

    void disableShortUrl(String urlToDisable);


}
