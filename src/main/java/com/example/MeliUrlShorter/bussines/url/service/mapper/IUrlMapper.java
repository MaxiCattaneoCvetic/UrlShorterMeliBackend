package com.example.MeliUrlShorter.bussines.url.service.mapper;

import com.example.MeliUrlShorter.bussines.url.model.Url;

public interface IUrlMapper<T> {
    Url toUrl(T toBeMapped);

}
