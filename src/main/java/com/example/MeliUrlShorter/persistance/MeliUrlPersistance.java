package com.example.MeliUrlShorter.persistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeliUrlPersistance implements IMeliPersistance {

    @Autowired
    private final RedisTemplate<String, String> shortMeliRedisPersistence;

    public MeliUrlPersistance(RedisTemplate<String, String> shortMeliRedisPersistence) {
        this.shortMeliRedisPersistence = shortMeliRedisPersistence;
    }


    @Override
    public void saveUrl(String hash, String urlToSave) {
        shortMeliRedisPersistence.opsForValue().set(hash, urlToSave);

    }

    @Override
    public void updateUrl(String hash, String urlToUpdate) {
        shortMeliRedisPersistence.opsForValue().set(hash, urlToUpdate);
    }

    @Override
    public String getUrlResolve(String hash) {
        return shortMeliRedisPersistence.opsForValue().get(hash);
    }

    @Override
    public void disableUrl(String hash) {
        shortMeliRedisPersistence.opsForValue().set(hash, ":active:false");
    }


}
