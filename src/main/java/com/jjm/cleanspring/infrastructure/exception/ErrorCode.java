package com.jjm.cleanspring.infrastructure.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /**
     * auth
     */
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-001", "만료된 access token 입니다."),
    MALFORMED_TOKEN_ACCESS(HttpStatus.BAD_REQUEST, "AUTH-002", "잘못된 형태의 토큰 요청입니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "AUTH-003", "허가되지 않은 접근입니다."),

    /*
     * valid
     */
    FAIL_INPUT_VALIDATION(HttpStatus.BAD_REQUEST, "VALID-001", "입력 값이 유효하지 않습니다: "),

    /**
     * error
     */
    UNEXPRECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR-001", "예상치 못한 에러가 발생했습니다. 같은 문제가 반복되면, 관리자에게 문의주세요.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
