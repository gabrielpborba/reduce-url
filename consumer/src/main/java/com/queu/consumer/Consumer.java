package com.queu.consumer;


import com.queu.consumer.config.RabbitConfig;
import com.queu.consumer.model.Url;
import com.queu.consumer.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  static final Logger logger = LoggerFactory.getLogger(Consumer.class);

  @Autowired
  private UrlRepository urlRepository;

  @RabbitListener(queues = RabbitConfig.QUEUE_URL)
  public void saveUrl(Url url) {
    logger.info("URL RECEIVED: SHORT URL: " + url.getShortUrl() + "   FULL URL: " + url.getFullUrl());

    Url urlReturned = urlRepository.save(url);
    logger.info("URL SAVE :)");

  }

}