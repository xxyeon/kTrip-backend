package com.example.ktrip.gijin.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Date;

@Component
public class JWTUtil
{
    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret)
    {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());//프로퍼티에 저장되 ㄴ키 가져오기
    }
    public String getType(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("type", String.class);
    }
    public String getId(String token)
    {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id",String.class);
    }
    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    public String createJwt(String id,String type, Long expiredMs) {

        return Jwts.builder()
                .claim("id", id)
                .claim("type",type)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }


}
