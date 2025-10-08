package com.prabhas.model.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.prabhas.model.token.JwtUtil;

@Configuration
public class JwtConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}