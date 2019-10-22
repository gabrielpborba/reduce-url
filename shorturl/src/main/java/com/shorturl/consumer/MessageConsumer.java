package com.shorturl.consumer;

import com.shorturl.dao.UrlRepository;
import com.shorturl.model.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
public class MessageConsumer {

  static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

  @Autowired
  private UrlRepository urlRepository;

  //@RabbitListener(queues = RabbitConfig.QUEUE_URL)
  public void processOrder(Url url) {
    logger.info("URL RECEIVED: SHORT URL: " + url.getShortUrl() + "   FULL URL: " + url.getFullUrl());

    Url urlReturned = urlRepository.save(url);

  }
}