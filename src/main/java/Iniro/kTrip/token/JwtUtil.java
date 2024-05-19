package Iniro.kTrip.token;

import Iniro.kTrip.dto.JwtToken;
import Iniro.kTrip.dto.MemberDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
    private final Key key;
    private final long accessTokenExpTime;

    // application.yml에서 secret 값 가져와서 key에 저장
    public JwtUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration_time}") long accessTokenExpTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime=accessTokenExpTime;
    }

    // Access Token 생성
    public String createAccessToken(MemberDto member){
        return createToken(member, accessTokenExpTime);
    }

    // JWT 생성
    private String createToken(MemberDto member, long expireTime){
        Claims claims = Jwts.claims();
        claims.put("memberId", member.getMember_id());
        claims.put("id", member.getId());
        claims.put("email", member.getEmail());
        claims.put("nickname", member.getNickname());
        claims.put("name", member.getName());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValiaity = now.plusSeconds(expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValiaity.toInstant()))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    //
    // Token에서 User ID 추출
    //

    public Long getUserId(String token){
        return parseClaims(token).get("memberId", Long.class);
    }

    //
    // JWT 검증
    //
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        }catch(ExpiredJwtException e){
            log.info("Expired JWT Token", e);
        }catch(UnsupportedJwtException e){
            log.info("Unsupported JWT Token", e);
        }catch(IllegalArgumentException e){
            log.info("JWT claims string is empty", e);
        }
        return false;
    }

    //
    // JWT Claims 추출
    //
    public Claims parseClaims(String accessToken){
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }
    }
}