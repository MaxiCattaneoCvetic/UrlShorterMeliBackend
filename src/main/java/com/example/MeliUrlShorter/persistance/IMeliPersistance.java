package com.example.MeliUrlShorter.persistance;


import com.example.MeliUrlShorter.bussines.url.model.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMeliPersistance extends CrudRepository<Url, String> {

//    void saveUrl(String hash, Url urlToSave);
//
//    void updateUrl(String hash, Url urlToUpdate);
//
//    Url getUrlResolve(String hash);
//
//    void disableUrl(String hash,Object objectToDisable);
//
//    void enableUrl(String hash, String urlEnable);
//
//    Object findObjectByHash(String hash,String key);

}


