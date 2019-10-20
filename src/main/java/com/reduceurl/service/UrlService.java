package com.reduceurl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.reduceurl.config.RabbitConfig;
import com.reduceurl.consumer.MessageConsumer;
import com.reduceurl.dao.UrlRepository;
import com.reduceurl.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

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

    public String getFullUrlByid(String id){
        Url url = urlRepository.findByShortUrl(id);

        return url.getFullUrl();
    }
}
