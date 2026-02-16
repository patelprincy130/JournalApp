package com.example.journalApp.config;

import com.example.journalApp.filters.JwtFilter;
import com.example.journalApp.service.UserDetailsServiceImplt;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.InvalidSessionStrategy;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //Authorization
        http.
                authorizeRequests() //to start authorizing requests coming on journal, other than all reqs are permitted to go without authorization.
                .antMatchers("/journals/**","/user/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();
//                .and()  //direct applies to http.
//                .httpBasic();      //removed http basic authentication so default login auth will be enabled. and before that our jwtfilter
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();
        //http.csrf().disable();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    private UserDetailsServiceImplt userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Authentication - username and password related
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); //this finds user from DB and compares encoded password --> if password is correct then automatically username and password are stored in securityContextHolder
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
