package com.example.MeliUrlShorter.presentation.controller.req;

public record RequestUrl(
    String protocol,
    String domain,
    String tld,
    String port,
    String route

) {
}
