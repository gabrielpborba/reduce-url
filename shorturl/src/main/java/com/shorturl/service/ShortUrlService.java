package com.shorturl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.shorturl.config.RabbitConfig;
import com.shorturl.dao.ShortUrlRepository;
import com.shorturl.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;

@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlRepository urlRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final RabbitTemplate rabbitTemplate;

    static final Logger logger = LoggerFactory.getLogger(ShortUrlService.class);

    @Autowired
    public ShortUrlService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public String createShortUrl(String url){
        String shortUrl = Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();

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

    public String getFullUrlByShortUrl(String id){
        Url url = urlRepository.findByShortUrl(id);
        return url.getFullUrl();
    }

    public String getShortUrlByFullUrl(String fullUrl){
        Url url = urlRepository.findByFullUrl(fullUrl);
        return url.getShortUrl();
    }

    public void deleteUrl(String url){
        try {
            urlRepository.deleteById(url);
            logger.info("URL DELETED: {}", url);
        }catch (Exception e){
            logger.error("Error trying to delete an url {}", url);

        }
    }

    public void deleteAll(){
        urlRepository.deleteAll();
    }

    public List<Url> findAll(){
        return urlRepository.findAll();
    }
}
