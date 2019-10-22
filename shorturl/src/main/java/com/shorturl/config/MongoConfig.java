package com.shorturl.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName(){return "reduceurldb";}

    @Override
    public MongoClient mongoClient(){return new MongoClient("mongo", 27017);}

}
