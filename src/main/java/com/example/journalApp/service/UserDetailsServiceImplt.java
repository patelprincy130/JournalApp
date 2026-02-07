package com.example.journalApp.service;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImplt implements UserDetailsService { //how to fetch user from DB by username
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException { //we want to test this method,
        UserEntity user=userRepository.findByUserName(userName);   //but we dont want to test this method of userRepo, so we can create mock obj of userRepo so instead of going to DB everytime we can provide fake data.
        if(user!=null){
            UserDetails userRes= User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userRes;
        }
        throw new UsernameNotFoundException("User not found with username: "+user);

    }
}
