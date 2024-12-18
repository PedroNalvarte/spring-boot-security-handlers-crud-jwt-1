package com.example.crudv2.crudlogin.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.crudv2.crudlogin.config.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtTokenProvider {

    private final String jwtSecret;
    private final Long jwtExpirationInMs;

    @Autowired
    public JwtTokenProvider(JwtProperties jwtProperties){
        this.jwtSecret = jwtProperties.getJwtSecret();
        this.jwtExpirationInMs = jwtProperties.getJwtExpirationInMs();

    }

    public String generateToken(String username){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String getUsernameFromToken(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(jwtSecret))
                .build()
                .verify(token);

        return decodedJWT.getSubject();
    }

    public boolean validateToken(String token){
        try{
            JWTVerifier verifier= JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build();

            verifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {

            System.out.println("JWT Token invalido: " + ex.getMessage());
            return false;

        }
    }


}
