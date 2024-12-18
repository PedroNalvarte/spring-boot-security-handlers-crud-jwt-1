package com.example.crudv2.crudlogin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Long jwtExpirationInMs;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public Long getJwtExpirationInMs() {
        return jwtExpirationInMs;
    }
}
