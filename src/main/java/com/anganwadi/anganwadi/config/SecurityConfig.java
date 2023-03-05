package com.anganwadi.anganwadi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private static final String[] WHITE_LIST_URL= {
            "/user/sendOtp",
            "/user/verifyOtp"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(WHITE_LIST_URL)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();

        return httpSecurity.build();

    }

}
