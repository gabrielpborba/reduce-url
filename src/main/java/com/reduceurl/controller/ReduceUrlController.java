package com.reduceurl.controller;

import com.reduceurl.dao.UrlRepository;
import com.reduceurl.json.UrlJson;
import com.reduceurl.model.Url;
import com.reduceurl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class ReduceUrlController {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @PostMapping("/reduce")
    @ResponseBody
    public String reduce(@RequestBody UrlJson urlJson){

      return "http://localhost:8080/redirect/" + urlService.createShortUrl(urlJson.getUrl());

    }

    @RequestMapping("/redirect/{url}")
    public void  redirect(@PathVariable String url,  HttpServletResponse httpServletResponse) throws IOException {
        String fullUrl = urlService.getFullUrlByid(url);
        httpServletResponse.sendRedirect(fullUrl);

    }

    @RequestMapping("/all")
    public @ResponseBody
    ResponseEntity<Object> findAll()  {
       List<Url> list =  urlRepository.findAll();
        return new ResponseEntity<Object>(list, HttpStatus.OK);

    }



    @RequestMapping("/redirect2/{url}")
    public RedirectView  redirect2(@PathVariable String url) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://twitter.com");
        return redirectView;
    }

}
