package com.shorturl.service;

import com.shorturl.dao.ShortUrlRepository;
import com.shorturl.model.Url;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestShortUrlService {

    private MockMvc mockMvc;

    @Autowired
    private ShortUrlService shortUrlService;

    @MockBean
    private ShortUrlRepository shortUrlRepository;


    @Test
    public void testGetFullUrlByShortUrl() {
        Url url = new Url();
        url.setFullUrl("https://globoesporte.globo.com");
        url.setShortUrl("hjh21h312");
        Mockito.when(shortUrlRepository.findByShortUrl("hjh21h312")).thenReturn(url);
        String response = shortUrlService.getFullUrlByShortUrl("hjh21h312");

        assertEquals(response, url.getFullUrl());
    }


    @Test
    public void testGetShortUrlByFullUrl() {
        Url url = new Url();
        url.setFullUrl("https://globoesporte.globo.com");
        url.setShortUrl("hjh21h312");
        Mockito.when(shortUrlRepository.findByFullUrl("https://globoesporte.globo.com")).thenReturn(url);
        String response = shortUrlService.getShortUrlByFullUrl("https://globoesporte.globo.com");

        assertEquals(response, url.getShortUrl());
    }

}
