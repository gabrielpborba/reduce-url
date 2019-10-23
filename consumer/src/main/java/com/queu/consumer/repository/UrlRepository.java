package com.queu.consumer.repository;

import com.queu.consumer.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {


}
