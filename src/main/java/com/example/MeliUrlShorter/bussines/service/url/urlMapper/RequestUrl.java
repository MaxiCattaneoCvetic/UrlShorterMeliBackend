package com.example.MeliUrlShorter.bussines.service.url.urlMapper;


import com.example.MeliUrlShorter.bussines.model.Url;
import org.springframework.stereotype.Component;

@Component
public class RequestUrl implements IUrlMapper<com.example.MeliUrlShorter.presentation.controller.req.RequestUrl> {


    @Override
    public Url toUrl(com.example.MeliUrlShorter.presentation.controller.req.RequestUrl toBeMapped) {
        return new Url(
                toBeMapped.protocol(),
                toBeMapped.domain(),
                toBeMapped.tld(),
                toBeMapped.port(),
                toBeMapped.route()
        );
    }


}
