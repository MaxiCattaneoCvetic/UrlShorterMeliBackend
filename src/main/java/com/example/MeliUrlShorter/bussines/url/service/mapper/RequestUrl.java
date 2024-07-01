package com.example.MeliUrlShorter.bussines.url.service.mapper;


import com.example.MeliUrlShorter.bussines.url.model.Url;
import org.springframework.stereotype.Component;

@Component
public class RequestUrl implements IUrlMapper<com.example.MeliUrlShorter.presentation.controller.req.RequestUrl> {


    @Override
    public Url toUrl(com.example.MeliUrlShorter.presentation.controller.req.RequestUrl toBeMapped) {
        String protocol = toBeMapped.protocol();
        String port = toBeMapped.port();
        String route = toBeMapped.route();
        String tld = toBeMapped.tld();
        // Si el protocolo contiene :// esta ok lo dejamos asi, pero sino le agregamos http://
        protocol = protocol.contains("://") ? protocol : "http://" ;
        protocol = protocol.startsWith("www.") ? protocol.replace("www.", "http://") : protocol;
        port = (!port.startsWith(":") && !port.isEmpty()) ? ":" + port : port;
        route = route.startsWith("/") ? route: "/" + route;
        tld = tld.startsWith(".") ? tld : "." + tld;

        // El navegador por defecto redirecciona a https, por lo tanto si la web que estamos consultando tiene certificado lo redireccionara a https
        // Caso contrario si el navegador no tiene certificado e intentamos ingresar con https nos arrojara un error
        // En conclusión, nos conviene mappear la solicitud a http

        return new Url(
                protocol,
                toBeMapped.domain(),
                tld,
                port,
                route,
                true
//                protocol+ toBeMapped.domain()+ tld+ port+ route
        );

    }



}
