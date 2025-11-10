package com.droidchat.DroidChat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for testing APIs via Postman
                .authorizeHttpRequests(auth -> auth
                                // ******** Below are Public APIs ***********
//                        .requestMatchers("/api/v1/users/register", "/api/v1/users/login", "/api/v1/users/sendOtp/**", "/api/v1/users/verifyOtp/*/*", "/api/v1/users/changePassword").permitAll() // allow register API without auth
//                        .anyRequest().authenticated() // other endpoints require authentication

                                // ******** Make all APIs public ***********
                                .anyRequest().permitAll() // Allow ALL endpoints without authentication
                )
                .httpBasic(basic -> {}); // enable basic auth for other endpoints

        return http.build();
    }

}
