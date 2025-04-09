package com.jjm.cleanspring.infrastructure.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey key;
    private final long TOKEN_EXPIRATION_TIME = 1000L * 60 * 60;                    // 1시간
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 30;  // 30일

    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰 생성
     *
     * @param id       사용자 ID
     * @param userName 사용자명
     * @return
     */
    public String generateToken(String id,
                                String userName) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                   .subject(id)
                   .claim("userName", userName)
                   .issuedAt(now)
                   .expiration(expiryDate)
                   .signWith(key)
                   .compact();
    }

    /**
     * 리프레시 토큰 생성
     *
     * @param id       사용자 ID
     * @param userName 사용자명
     * @return
     */
    public String generateRefreshToken(String id,
                                       String userName) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                   .subject(id)
                   .claim("userName", userName)
                   .issuedAt(now)
                   .expiration(expiryDate)
                   .signWith(key)
                   .compact();
    }

    /**
     * 토큰에서 사용자 ID 추출 (subject)
     *
     * @param token
     * @return
     */
    public String extractId(String token) {
        return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }

    /**
     * 토큰에서 사용자명 추출 (userName 클레임)
     *
     * @param token
     * @return
     */
    public String extractUserName(String token) {
        return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .get("userName", String.class);
    }

    /**
     * 토큰 유효성 검사
     *
     * @param token
     * @return
     */
    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;

        }
        catch (SecurityException |
               MalformedJwtException e) {
            System.out.println("Invalid JWT Token");
        }
        catch (ExpiredJwtException e) {
            System.out.println("Expired JWT Token");
        }
        catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT Token");
        }
        catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty.");
        }

        return false;
    }
}
