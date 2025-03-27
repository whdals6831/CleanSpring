package com.jjm.cleanspring.adapter.in.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * 공통 에러 (1000번대)
     */
    INTERNAL_SERVER_ERROR("E1001", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    VALIDATION_FAILED("E1002", HttpStatus.BAD_REQUEST, "입력한 값이 올바른 형태가 아닙니다."),
    BAD_REQUEST("E1003", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND("E1004", HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED("E1005", HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드가 호출되었습니다."),

    /**
     * 인증 관련 에러 (2000번대)
     */
    ACCESS_TOKEN_EXPIRED("E2001", HttpStatus.UNAUTHORIZED, "만료된 Access Token 입니다."),
    FORBIDDEN_ACCESS("E2002", HttpStatus.FORBIDDEN, "권한이 없는 사용자의 요청입니다."),
    UNAUTHORIZED_REQUEST("E2003", HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자의 요청입니다.");

    private final String errorCode;
    private final HttpStatus status;
    private final String message;

    public int getSatusCode() {
        return this.status.value();
    }
}