package com.example.journalApp.service;

import com.example.journalApp.entity.UserEntity;
import com.example.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testFindByUserName(){
        assertNotNull(userRepository.findByUserName("Tia"));
    }

    @Test
    public void testUserJournalsNotNull(){
        Assertions.assertTrue(!userRepository.findByUserName("Tia").getJournalEntityList().isEmpty());
    }


    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "4,5,9",
            "3,3,8"  //this will fail
    })
    public void add(int a,int b,int expected){
        Assertions.assertEquals(expected,a+b);
    }

    @ParameterizedTest
    @ValueSource(strings = {  //EnumSource can also be used
            "Tia",
            "Lea",
            "Lee",
            "Vee"
    })
//    @CsvSource({
//            "Tia",
//            "Lea",
//            "Lee",
//            "Vee"
//    })
    public void testUserNameFind(String name){
        Assertions.assertNotNull(userRepository.findByUserName(name));
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(UserEntity user){ //test for creating new user using Builder
        userService.saveNewUser(user);
    }
}
