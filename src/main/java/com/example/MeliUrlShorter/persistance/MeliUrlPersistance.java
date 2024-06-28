package com.example.MeliUrlShorter.persistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class MeliUrlPersistance implements IMeliPersistance {

    @Autowired
    private  RedisTemplate redisTemplate;

    private static final String KEY = "URL";


    @Override
    public void saveUrl(String hash, String urlToSave) {
        redisTemplate.opsForValue().set(hash, urlToSave);

    }

    @Override
    public void updateUrl(String hash, String urlToUpdate) {
        redisTemplate.opsForValue().set(hash, urlToUpdate);
    }

    @Override
    public String getUrlResolve(String hash) {
        return (String) redisTemplate.opsForValue().get(hash);
    }

    @Override
    public void disableUrl(String hash) {
        redisTemplate.opsForValue().set(hash, ":active:false");
    }


}
