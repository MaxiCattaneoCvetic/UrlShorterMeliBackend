//package com.example.MeliUrlShorter.persistance;
//
//import com.example.MeliUrlShorter.bussines.url.model.Url;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//
//
//
//public class MeliUrlPersistance  {
//
//    @Autowired
//    private  RedisTemplate redisTemplate;
//    private final String KEY = "URL";
//    private final String KEY_OFF = "URL_OFF";
//
//    private final HashOperations<String, String, Url> hashOperations;
//
//    public MeliUrlPersistance(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//        this.hashOperations = redisTemplate.opsForHash();
//    }
//
//
//
//
//    public void saveUrl(String hash, Url urlToSave) {
//        hashOperations.put(KEY, hash, urlToSave);
//
//    }
////
////    @Override
////    public void updateUrl(String hash, Url urlToUpdate) {
////       hashOperations.put(KEY, hash, urlToUpdate);
////    }
////
////    @Override
////    public Url getUrlResolve(String hash) {
////        return hashOperations.get(KEY, hash);
////    }
////
////
////    @Override
////    public void disableUrl(String hash,Object objectToDisable) {
//////        hashOperations.put(KEY,hash,objectToDisable+":DISABLED");
////    }
////
////    @Override
////    public Object findObjectByHash(String hash,String key) {
////        return hashOperations.get(key, hash);
////    }
////
////
////
////    @Override
////    public void enableUrl(String hash, String urlEnable) {
//////        hashOperations.put(KEY,hash,urlEnable);
////    }
//
//
//}
