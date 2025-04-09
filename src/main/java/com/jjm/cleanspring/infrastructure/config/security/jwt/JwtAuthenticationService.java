package com.jjm.cleanspring.infrastructure.config.security.jwt;

import com.jjm.cleanspring.infrastructure.config.security.service.CustomUserDetails;
import com.jjm.cleanspring.infrastructure.exception.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
    private JwtProvider jwtProvider;
    private UserDetailsService userDetailsService;

    /**
     * JWT 토큰을 검증하고 사용자 정보를 로드한 뒤,
     * Spring Security의 Authentication 객체를 만들어 반환합니다.
     */
    public Authentication authenticateToken(String token) {
        try {
            // 1) 토큰 유효성 확인
            if (!jwtProvider.validateToken(token)) {
                throw new JwtAuthenticationException("Invalid JWT token");
            }

            String userId = jwtProvider.extractId(token);             // id 추출
            String username = jwtProvider.extractUserName(token);     // username 추출
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userId, // principal = id
                    token,
                    userDetails.getAuthorities()
            );

            // userName 등 추가 정보
            authToken.setDetails(userDetails);

            return authToken;
        }
        catch (Exception e) {
            throw new JwtAuthenticationException("JWT authentication failed");
        }
    }
}
