package com.reduceurl.controller;

import com.reduceurl.json.UrlJson;
import com.reduceurl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ReduceUrlController {

    @Autowired
    private UrlService urlService;

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

    @RequestMapping("/redirect2/{url}")
    public RedirectView  redirect2(@PathVariable String url) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("https://twitter.com");
        return redirectView;
    }

}
