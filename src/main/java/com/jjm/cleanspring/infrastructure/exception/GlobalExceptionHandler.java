package com.jjm.cleanspring.infrastructure.exception;

import com.jjm.cleanspring.adapter.in.web.dto.ErrorCode;
import com.jjm.cleanspring.adapter.in.web.dto.ResponseDto;
import com.jjm.cleanspring.application.exception.JwtAuthenticationException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * 잘못된 값이 전달되었을 때 예외 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorCode errorCode = ErrorCode.BAD_REQUEST;

        return ResponseEntity.status(errorCode.getStatus())
                             .body(ResponseDto.error(errorCode, e.getMessage()));
    }

    /**
     * 요청한 데이터가 없을 때 예외 처리
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseDto<Object>> handleNoSuchElementException(NoSuchElementException e) {
        ErrorCode errorCode = ErrorCode.NOT_FOUND;

        return ResponseEntity.status(errorCode.getStatus())
                             .body(ResponseDto.error(errorCode, e.getMessage()));
    }

    /**
     * Jwt 인증 예외 처리
     */
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ResponseDto<Object>> handleJwtAuthenticationException(JwtAuthenticationException e) {
        ErrorCode errorCode = ErrorCode.INVALID_JWT_ERROR;

        return ResponseEntity.status(errorCode.getStatus())
                             .body(ResponseDto.error(errorCode, e.getMessage()));
    }

    /**
     * 사용자 인증 관련 예외 처리
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDto<Object>> handleAuthenticationException(AuthenticationException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED_REQUEST;

        return ResponseEntity.status(errorCode.getStatus())
                             .body(ResponseDto.error(errorCode, e.getMessage()));
    }

    /**
     * 접근 권한 예외 처리
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDto<Object>> handleAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.FORBIDDEN_ACCESS;

        return ResponseEntity.status(errorCode.getStatus())
                             .body(ResponseDto.error(errorCode));
    }

    /**
     * 입력값 유효성 처리 예외 처리
     * -> SelfValidating 에서 검증 실패된 입력값
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDto<Object>> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorCode errorCode = ErrorCode.VALIDATION_FAILED;

        return ResponseEntity.status(errorCode.getStatus())
                             .body(ResponseDto.error(errorCode, e.getMessage()));
    }

    /**
     * 예상치 못한 내부 서버 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleException(Exception e) {
        log.error("Unhandled Exception: ", e);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(errorCode.getStatus())
                             .body(ResponseDto.error(errorCode));
    }
}