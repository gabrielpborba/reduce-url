package com.shorturl.controller;

import com.shorturl.dao.ShortUrlRepository;
import com.shorturl.json.UrlJson;
import com.shorturl.model.Url;
import com.shorturl.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Value("${reduce.url}")
    private String host;

    @PostMapping("/reduce")
    @ResponseBody
    public String reduce(@RequestBody UrlJson urlJson){
      return host + "/redirect/" + shortUrlService.createShortUrl(urlJson.getUrl());
    }

    @RequestMapping("/redirect/{url}")
    public void  redirect(@PathVariable String url,  HttpServletResponse httpServletResponse) throws IOException {
        String fullUrl = shortUrlService.getFullUrlByShortUrl(url);
        String newURL = httpServletResponse.encodeRedirectURL(fullUrl);
        httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        httpServletResponse.setHeader("Location", newURL);

    }

    @RequestMapping("/findByFullUrl")
    public ResponseEntity<Object> findByFullUrl(@RequestParam String fullUrl){
        String url = shortUrlService.getShortUrlByFullUrl(fullUrl);
        return new ResponseEntity<Object>(url, HttpStatus.OK);
    }

    @RequestMapping("/findByShortUrl/{shortUrl}")
    public ResponseEntity<Object> findByShortUrl(@PathVariable String shortUrl){
        String url = shortUrlService.getFullUrlByShortUrl(shortUrl);
        return new ResponseEntity<Object>(url, HttpStatus.OK);
    }

    @RequestMapping("/findAll")
    public @ResponseBody
    ResponseEntity<List<Url>> findAll()  {
       List<Url> list =  shortUrlService.findAll();
       return new ResponseEntity<List<Url>>(list, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public HttpStatus deleteAll(){
        shortUrlService.deleteAll();
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{shortUrl}")
    public HttpStatus deleteUrl(@PathVariable String shortUrl) {
       shortUrlService.deleteUrl(shortUrl);
        return HttpStatus.OK;
    }

}
