package com.shorturl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.mongodb.BasicDBObject;
import com.shorturl.config.RabbitConfig;
import com.shorturl.consumer.MessageConsumer;
import com.shorturl.dao.UrlRepository;
import com.shorturl.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Date;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    public UrlService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public String createShortUrl(String url){
        String shortUrl = Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();
        Date now = new Date();

        BasicDBObject timeNow = new BasicDBObject("date", now);
        Url newUrl = new Url();
        newUrl.setFullUrl(url);
        newUrl.setShortUrl(shortUrl);

        try {
            this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_URL, newUrl);
        }catch (Exception e){
            logger.error("RABBIT MQ IS DOWN!");
        }

        return shortUrl;

    }

    public String getFullUrlById(String id){
        Url url = urlRepository.findByShortUrl(id);
        return url.getFullUrl();
    }
}
