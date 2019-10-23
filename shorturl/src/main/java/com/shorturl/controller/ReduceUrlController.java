package com.shorturl.controller;

import com.shorturl.dao.UrlRepository;
import com.shorturl.json.UrlJson;
import com.shorturl.model.Url;
import com.shorturl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
      //colocar host em variavel externa
      return "http://localhost:8080/redirect/" + urlService.createShortUrl(urlJson.getUrl());

    }

    @RequestMapping("/redirect/{url}")
    public void  redirect(@PathVariable String url,  HttpServletResponse httpServletResponse) throws IOException {
        String fullUrl = urlService.getFullUrlByShortUrk(url);
        String newURL = httpServletResponse.encodeRedirectURL(fullUrl);
        httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        httpServletResponse.setHeader("Location", newURL);

    }

    @RequestMapping("/findByFullUrl/{fullUrl}")
    public ResponseEntity<Object> findByFullUrl(@PathVariable String fullUrl){
        String url = urlService.getShortUrlByFullUrl(fullUrl);
        return new ResponseEntity<Object>(url, HttpStatus.OK);
    }

    @RequestMapping("/findByShortUrl/{shortUrl}")
    public ResponseEntity<Object> findByShortUrl(@PathVariable String shortUrl){
        String url = urlService.getFullUrlByShortUrk(shortUrl);
        return new ResponseEntity<Object>(url, HttpStatus.OK);
    }

    @RequestMapping("/findAll")
    public @ResponseBody
    ResponseEntity<Object> findAll()  {
       List<Url> list =  urlRepository.findAll();
        return new ResponseEntity<Object>(list, HttpStatus.OK);

    }

    @DeleteMapping("/deleteAll")
    public HttpStatus deleteAll(){
        urlRepository.deleteAll();
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{shortUrl}")
    public ResponseEntity deleteUrl(@PathVariable String shortUrl) {
       urlRepository.deleteById(shortUrl);
       return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

}
