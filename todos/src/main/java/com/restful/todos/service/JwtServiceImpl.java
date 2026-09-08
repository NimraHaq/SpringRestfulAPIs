package com.restful.todos.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expiration}")
    private long JWT_EXPIRATION;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSignedKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public String generateToken(Map<String, Claims> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSignedKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignedKey() {
        byte[] secretKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretKey);
    }
}
