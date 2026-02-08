package com.example.journalApp.service;

import com.example.journalApp.entity.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${weather.api.url}")
    private String api;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getResponse(String city){
        String finalApi=api.replace("{KEY}",apiKey).replace("{CITY}",city);
        ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
        return response.getBody();
    }
}
