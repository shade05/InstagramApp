package com.codepath.courses.instagramapp.service.impl;

import com.codepath.courses.instagramapp.service.InstagramClient;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by deepaks on 12/2/15.
 */

public class InstagramClientApiImpl implements InstagramClient {

    private static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";

    private static final String URL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

    @Override
    public String getPopularMedia() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        String result = restTemplate.getForObject(URL, String.class);
        return result;
    }
}
