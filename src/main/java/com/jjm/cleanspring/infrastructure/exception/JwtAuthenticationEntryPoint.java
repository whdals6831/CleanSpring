package com.jjm.cleanspring.infrastructure.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * filter에서 발생하는 예외를 @ControllerAdvice에서 예외 처리하도록 위임
     *
     * @param request       클라이언트에서 서블릿에 요청하는 객체
     * @param response      서블릿에서 클라이언트로 보내는 응답 객체
     * @param authException 인증 관련 예외
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // filter에서 던지는 예외
        Exception requestException = (Exception) request.getAttribute("exception");

        resolver.resolveException(request, response, null, requestException != null ? requestException : authException);
    }
}
