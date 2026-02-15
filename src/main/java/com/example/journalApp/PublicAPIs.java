package com.example.journalApp;

import com.example.journalApp.config.JwtUtil;
import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.service.UserDetailsServiceImplt;
import com.example.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/public")
@Slf4j
public class PublicAPIs {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImplt userDetailsServiceImplt;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public void signup(@RequestBody UserEntity user){
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserEntity user){
        try{
           Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));
           //this ensures the username and password is correct, internally uses UserDetailsServiceImplt to check. also check encoded pwd
            UserDetails userDetails=userDetailsServiceImplt.loadUserByUsername(user.getUserName());
            String token=jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception in login ",e);
            return new ResponseEntity<>("Wrong username or password",HttpStatus.BAD_REQUEST);
        }
    }

}
