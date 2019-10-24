package com.shorturl.controller;

import com.shorturl.json.UrlJson;
import com.shorturl.model.Url;
import com.shorturl.service.ShortUrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestUrlController {

    @Mock
    private ShortUrlService shortUrlService;

    @InjectMocks
    private ShortUrlController reduceUrlController;

    private MockMvc mockMvc;

    @Test
    public void testReduce(){
        Mockito.when(shortUrlService.createShortUrl("https://globoesporte.globo.com")).thenReturn("hjh21h312");
        UrlJson urlJson = new UrlJson();
        urlJson.setUrl("https://globoesporte.globo.com");
        String response = reduceUrlController.reduce(urlJson);
        assertEquals("null/redirect/hjh21h312", response);
    }

   /* @Test
    public void testRedirect() throws Exception {
        Mockito.when(urlService.getFullUrlByShortUrk("hjh21h312")).thenReturn("https://globoesporte.globo.com");
        this.mockMvc.perform(MockMvcRequestBuilders.get("redirect/hjh21h312")).andExpect(MockMvcResultMatchers.status().isOk());

    }*/

   @Test
    public void testFindByFullUrl(){
       Mockito.when(shortUrlService.getShortUrlByFullUrl("https://globoesporte.globo.com")).thenReturn("hjh21h312");
       ResponseEntity<Object> response = reduceUrlController.findByFullUrl("https://globoesporte.globo.com");
       assertEquals("hjh21h312", response.getBody());
   }

   @Test
    public void testFindByShortUrl(){
       Mockito.when(shortUrlService.getFullUrlByShortUrl("hjh21h312")).thenReturn("https://globoesporte.globo.com");
       ResponseEntity<Object> response = reduceUrlController.findByShortUrl("hjh21h312");
       assertEquals("https://globoesporte.globo.com", response.getBody());
   }

   @Test
    public void testFindAll(){
       List<Url> list = new ArrayList<>();

       Url url1 = new Url();
       url1.setFullUrl("https://globoesporte.globo.com");
       url1.setShortUrl("hjh21h312");

       Url url2= new Url();
       url2.setFullUrl("https://uol.com.br");
       url2.setShortUrl("fgfdsfgr");

       list.add(url1);
       list.add(url2);

        Mockito.when(shortUrlService.findAll()).thenReturn(list);

       ResponseEntity<List<Url>> response = reduceUrlController.findAll();
       assertEquals(list.get(0).getFullUrl(), response.getBody().get(0).getFullUrl());
       assertEquals(list.get(0).getShortUrl(), response.getBody().get(0).getShortUrl());
       assertEquals(list.get(1).getFullUrl(), response.getBody().get(1).getFullUrl());
       assertEquals(list.get(1).getShortUrl(), response.getBody().get(1).getShortUrl());
   }
}
