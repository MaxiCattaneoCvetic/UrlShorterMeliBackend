package com.example.MeliUrlShorter.bussines.service.url.urlMapper;

import com.example.MeliUrlShorter.bussines.model.Url;

public interface IUrlMapper<T> {
    Url toUrl(T toBeMapped);

}
