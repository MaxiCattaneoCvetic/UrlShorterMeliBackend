package com.example.MeliUrlShorter.bussines.url.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Url")
public class Url implements Serializable {

    @Id
    private String hash;
    private String protocol;
    private String domain;
    private String tld;
    private String port;
    private String route;
    private Boolean isActive;

    public Url() {
    }

    public Url(String protocol, String domain, String tld, String port, String route, Boolean isActive) {
        this.protocol = protocol;
        this.domain = domain;
        this.tld = tld;
        this.port = port;
        this.route = route;
        this.isActive = isActive;
    }

    public Url(String hash, String protocol, String domain, String tld, String port, String route) {
        this.hash = hash;
        this.protocol = protocol;
        this.domain = domain;
        this.tld = tld;
        this.port = port;
        this.route = route;
    }

    public Url(String protocol, String domain, String tld, String port, String route) {
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

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return protocol+domain+tld+port+route;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean isActive() {
        return isActive;
    }
}