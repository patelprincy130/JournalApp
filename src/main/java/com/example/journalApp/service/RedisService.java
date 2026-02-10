package com.example.journalApp.service;

import com.example.journalApp.entity.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> weatherResponseClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper objectMapper=new ObjectMapper();
            return objectMapper.readValue(o.toString(),weatherResponseClass);
        } catch (Exception e) {
            log.error("Error reading data, ",e);
            return null;
        }
    }

    public void set(String key,Object value, Long ttl){
        try{
            ObjectMapper objectMapper=new ObjectMapper();
            String valueAsString = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key,valueAsString,ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Error writing data ",e);
        }
    }

}
