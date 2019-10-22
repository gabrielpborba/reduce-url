package com.reduceurl.model;

import org.springframework.data.annotation.Id;

public class Url {

    public static final String COLLECTION_NAME = "url";

    @Id
    private String shortUrl;

    private String fullUrl;

    public Url(){}

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public Url(String shortUrl, String fullUrl) {
        this.shortUrl = shortUrl;
        this.fullUrl = fullUrl;
    }
}
