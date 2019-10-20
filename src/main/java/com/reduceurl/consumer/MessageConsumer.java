package com.reduceurl.consumer;

import com.reduceurl.config.RabbitConfig;
import com.reduceurl.dao.UrlRepository;
import com.reduceurl.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

  static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

  @Autowired
  private UrlRepository urlRepository;

  @RabbitListener(queues = RabbitConfig.QUEUE_URL)
  public void processOrder(Url url) {
    logger.info("URL RECEIVED: SHORT URL: " + url.getShortUrl() + "   FULL URL: " + url.getFullUrl());

    Url urlReturned = urlRepository.save(url);
    logger.info("URL RETURNED FROM MONGO:", urlReturned.toString());

  }
}