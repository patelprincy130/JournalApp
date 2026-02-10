package com.example.journalApp.service;

import com.example.journalApp.config.AppCache;
import com.example.journalApp.controller.ConstantsPlacholders;
import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.entity.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

//    @Value("${weather.api.url}")
//    private String api;  //from DB collectn

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getResponse(String city){
//        String finalApi=api.replace("<key>",apiKey).replace("<city>",city);
//        String finalApi=appCache.appCache.get("api").replace("<key>",apiKey).replace("<city>",city); //without enum


        WeatherResponse redisCacheResponse=redisService.get("weather_of_"+city,WeatherResponse.class);
        if(redisCacheResponse!=null){
            return redisCacheResponse;
        }else{
            String finalApi=appCache.appCache.get(AppCache.keys.API.toString()).replace(ConstantsPlacholders.API_KEY,apiKey).replace(ConstantsPlacholders.CITY,city);
            ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherResponse.class);
            WeatherResponse body=response.getBody();
            if(body!=null){
                redisService.set("weather_of_"+city,body,300L);
            }
            return body;
        }

    }

//    public WeatherResponse getResponse1(String city){
//        String finalApi=api.replace("{KEY}",apiKey).replace("{CITY}",city);
//
//        //to send post request, this data we send in body in JSON
//        UserEntity user=UserEntity.builder().userName("Jia").password("123Jia").roles(Arrays.asList("USER")).build();
//        //HttpEntity<UserEntity> httpEntity=new HttpEntity<>(user);
//        //If we want to send headers then
//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.add("key","value");
//        HttpEntity<UserEntity> httpEntity=new HttpEntity<>(user,httpHeaders); //overloaded
//
//        ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalApi, HttpMethod.POST,httpEntity, WeatherResponse.class);
//        return response.getBody();
//    }


}
