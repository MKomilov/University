package com.example.test.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * For generate token based on student's unique field(email) for 1 hour
 * getEmailFromToken() method is responsible for parsing token to get student email
 */
@Component
public class JwtProvider {
    @Value("${command.jwt-key}")
    String jwtKey;
    @Value("${command.expire-time}")
    Long expireTime;
    public String generateJwt(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expireTime))
                .signWith(SignatureAlgorithm.HS512,jwtKey)
                .compact();
    }

    public String  getEmailFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
