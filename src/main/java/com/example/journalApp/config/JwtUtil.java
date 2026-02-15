package com.example.journalApp.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtil {


    public String generateToken(String username) {
        Map<String,Object> claims=new HashMap<>();
        return createToken(username,claims);
    }

    public String createToken(String sub, Map<String,Object>claims){
        return Jwts.builder()
                .claims(claims)
                .subject(sub)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignKey())
                .compact();
    }

    private String secret="JSN9**!&343093290&&vadtal!*73823";
    public SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }


}
