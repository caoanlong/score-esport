package com.dragon.scoreapi.utils;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    private final String SCERET_KEY = "glsjgrjeio4903i094%^^8$$";
    private final Long EXPIRED_TIME = 3600000 * 24 * 30L;

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SCERET_KEY).parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private String extractId(String token) {
        return extractClaim(token, Claims::getId);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, JSONObject jsonObject) {
        return Jwts.builder()
                .setClaims(claims)
                .setId(jsonObject.getString("id"))
                .setSubject(jsonObject.getString("phone"))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS256, SCERET_KEY).compact();
    }

    public String generateToken(JSONObject jsonObject) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, jsonObject);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        String uname = userDetails.getUsername();
        return (username.equals(uname)) && !isTokenExpired(token);
    }
}
