package com.jjm.cleanspring.application.service.user.token;

import com.jjm.cleanspring.application.exception.JwtAuthenticationException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
     * @return 생성한 토큰 문자열
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
     * @return 생성한 리프레시 토큰 문자열
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
     * @param token 토큰 문자열
     * @return 사용자 ID
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
     * @param token 토큰 문자열
     * @return 사용자명
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
     * 토큰에서 만료일자 추출
     *
     * @param token 토큰 문자열
     * @return 만료일자
     */
    public LocalDateTime extractExpiredDate(String token) {
        return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getExpiration()
                   .toInstant()
                   .atZone(ZoneId.systemDefault())
                   .toLocalDateTime();
    }

    /**
     * 토큰 유효성 검사
     * 다음 조건을 만족해야 함
     * 1. 만료되지 않음
     * 2. 올바른 토큰 문자열
     * 3. 지원하는 토큰 형식
     *
     * @param token 토큰 문자열
     * @return 유효성 검사 통과 여부
     */
    public void validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        }
        catch (SecurityException |
               MalformedJwtException e) {
            throw new JwtAuthenticationException("Invalid JWT Token");
        }
        catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException("Expired JWT Token");
        }
        catch (UnsupportedJwtException e) {
            throw new JwtAuthenticationException("Unsupported JWT Token");
        }
        catch (IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT claims string is empty.");
        }
    }
}
