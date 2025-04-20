package com.jjm.cleanspring.infrastructure.config.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtAuthenticationService jwtAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1) Request Header에서 JWT 토큰 추출
            String token = extractToken(request);

            if (token != null && !token.isBlank()) {
                // 2) 토큰이 존재하면 인증 시도
                Authentication authentication = jwtAuthenticationService.authenticateToken(token);

                // 3) 인증 성공 시 SecurityContext에 등록
                SecurityContextHolder.getContext()
                                     .setAuthentication(authentication);
            }
        }
        catch (Exception e) {
            // Custom Exception을 EntryPoint에서 받을 수 있도록 담아줌
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     */
    private String extractToken(HttpServletRequest request) {
        String prefix = "Bearer ";
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith(prefix)) {
            return bearerToken.substring(prefix.length())
                              .trim();
        }

        return null;
    }
}
