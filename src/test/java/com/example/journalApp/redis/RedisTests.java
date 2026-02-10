package com.example.journalApp.redis;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void test(){
        //redisTemplate.opsForValue().set("name","Jsn"); //even after commenting this line, will get name, once it is set
        //Object o=redisTemplate.opsForValue().get("name");
        Object o=redisTemplate.opsForValue().get("salary");  //salary will be null, set from redis cli until and unless redisConfig
        int a=0;
    }
}
