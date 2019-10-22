package com.reduceurl.dao;

import com.reduceurl.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {

    public Url findByShortUrl(String shortUrl);

    public Url findByFullUrl(String fullUrl);

}
