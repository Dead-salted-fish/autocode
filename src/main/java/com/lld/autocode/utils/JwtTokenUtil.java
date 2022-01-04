package com.lld.autocode.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
@Component
public class JwtTokenUtil {
    private static long tokenExpiration = 60*60*24*365;
    private static String tokenSignKey = Base64.getEncoder().encodeToString("wzl".getBytes());
    private static String userRoleKey = "userRole";

    public String createToken(String userName) {
        String token = Jwts.builder().setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512,  tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public static String createToken(String userName, Long id) {
        String token = Jwts.builder().setSubject(userName)
                .claim("userId", id)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public static String createToken(String userName, String role) {
        String token = Jwts.builder().setSubject(userName)
                .claim(userRoleKey, role)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS512, tokenSignKey).compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public static String getUserNameFromToken(String token) {
        String userName = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }

    public static String getUserRoleFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody();
        return claims.get(userRoleKey).toString();
    }

    public static Long getUserRoIdFromToken(String token) {

        Claims claims = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody();
        return claims.get("userId")!=null? Long.valueOf(claims.get("userId").toString()):null;
    }

}