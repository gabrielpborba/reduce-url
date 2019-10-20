package com.reduceurl.service;

import com.google.common.hash.Hashing;
import com.reduceurl.dao.UrlRepository;
import com.reduceurl.model.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public String createShortUrl(String url){
        String shortUrl = Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();

        Url newUrl = new Url();
        newUrl.setFullUrl(url);
        newUrl.setShortUrl(shortUrl);

        //se fila cair retorna a url inteira


        //colocar na fila
        urlRepository.save(newUrl);

        return shortUrl;

    }

    public String getFullUrlByid(String id){
        Url url = urlRepository.findByShortUrl(id);

        return url.getFullUrl();
    }
}
