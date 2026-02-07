package com.example.journalApp.service;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;

import static org.mockito.Mockito.when;

//@SpringBootTest //creates ApplicationContext so all the beans are created, and DB connections are established.
//public class UserDetailsServiceImplntTest {
//
//    @Autowired
//    private UserDetailsServiceImplt userDetailsServiceImplt;
//
//    @MockBean //debug and see.. @Mock debug and see...
//    private UserRepository userRepository; //create mock obj of this
//
//    @Test
//    public void loadUserByUserNameTest(){
//        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(UserEntity.builder().userName("ooo").password("123ooo").roles(Arrays.asList("USER")).build());
//        UserDetails userDetails=userDetailsServiceImplt.loadUserByUsername("Shyam");
//        Assertions.assertNotNull(userDetails);
//    }
//}
 //-------------do debug for this code-----------------then see
//even for this code, ApplicationContext is created and app takes much time to start and we are still using userrepo, it is giving all info for "Shyam" not for "ooo".
//but we dont want to create ApplicationContext

 //Without creation of applicationContext -- takes less time to start--run faster
public class UserDetailsServiceImplntTest {

    @InjectMocks  //this will auto create the instace of userDetlImpt and finds dependencies with @Mock and inject into it. so userRepo will be injected in it auto
    private UserDetailsServiceImplt userDetailsServiceImplt;

    @Mock
    private UserRepository userRepository; //create mock obj of this

     @BeforeEach
     void setUp(){
         MockitoAnnotations.initMocks(this); //initialize all with @mock and then inject into @InjectMock
     }

    @Test
    public void loadUserByUserNameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(UserEntity.builder().userName("ooo").password("123ooo").roles(Arrays.asList("USER")).build());
        UserDetails userDetails=userDetailsServiceImplt.loadUserByUsername("Shyam");
        Assertions.assertNotNull(userDetails);
    }
}
