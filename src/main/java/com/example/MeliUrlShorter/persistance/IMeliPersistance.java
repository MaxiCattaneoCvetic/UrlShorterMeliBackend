package com.example.MeliUrlShorter.persistance;


import com.example.MeliUrlShorter.bussines.url.model.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IMeliPersistance extends CrudRepository<Url, String> {

    List<Url> findAll();


}


