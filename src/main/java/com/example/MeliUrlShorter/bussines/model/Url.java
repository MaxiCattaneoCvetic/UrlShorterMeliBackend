package com.example.MeliUrlShorter.bussines.model;


import java.io.Serializable;

public class Url implements Serializable {
    private String hash;
    private String protocol;
    private String domain;
    private String tld;
    private String port;
    private String route;

    public Url(String protocol, String domain, String tld, String port, String route) {
        this.protocol = protocol;
        this.domain = domain;
        this.tld = tld;
        this.port = port;
        this.route = route;
    }

    public Url(String hash, String protocol, String domain, String tld, String port, String route) {
        this.hash = hash;
        this.protocol = protocol;
        this.domain = domain;
        this.tld = tld;
        this.port = port;
        this.route = route;
    }

    public String hash() {
        return hash;
    }

    public String protocol() {
        return protocol;
    }

    public String domain() {
        return domain;
    }

    public String tld() {
        return tld;
    }

    public String port() {
        return port;
    }

    public String route() {
        return route;
    }

    @Override
    public String toString() {
        return protocol+domain+tld+port+route;
    }


}
