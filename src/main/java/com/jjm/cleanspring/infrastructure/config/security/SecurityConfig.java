package com.jjm.cleanspring.infrastructure.config.security;

import com.jjm.cleanspring.application.service.user.token.JwtProvider;
import com.jjm.cleanspring.infrastructure.config.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint entryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 설정 비활성화
        http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(
                    authorize -> authorize
                            .requestMatchers("/api-ui/**", "/api-docs/**", "/swagger-ui/**") // Swagger 관련 API 접근 허용
                            .permitAll()
                            .requestMatchers("/user/register", "/auth/login") // 회원가입, 로그인 허용
                            .permitAll()
                            .anyRequest()
                            .authenticated())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint));

        return http.build();
    }
}
