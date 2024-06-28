package com.example.MeliUrlShorter.persistance;


public interface IMeliPersistance {

    void saveUrl(String hash, String urlToSave);
    void updateUrl(String hash, String urlToUpdate);
    String getUrlResolve(String hash);

    void disableUrl(String hash);

}

