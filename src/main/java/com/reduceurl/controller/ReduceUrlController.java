package com.reduceurl.controller;

import com.reduceurl.json.UrlJson;
import com.reduceurl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReduceUrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/reduce")
    @ResponseBody
    public String reduce(@RequestBody UrlJson urlJson){

      return urlService.createShortUrl(urlJson.getUrl());

    }
}
