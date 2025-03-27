package com.jjm.cleanspring.adapter.in.web.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseDto<T> {
    private final Boolean isSuccess;
    private final String message;
    private final int statusCode;
    private final LocalDateTime timestamp;
    private final String errorCode;
    private final T data;

    private ResponseDto(Boolean isSuccess,
                        String message,
                        int statusCode,
                        LocalDateTime timestamp,
                        String errorCode,
                        T data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.data = data;
    }

    public static <T> ResponseDto<T> success() {
        return new ResponseDto<>(true, "요청이 성공적으로 처리되었습니다.", 200, LocalDateTime.now(), null, null);
    }

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, "요청이 성공적으로 처리되었습니다.", 200, LocalDateTime.now(), null, data);
    }

    public static <T> ResponseDto<T> success(T data, String message) {
        return new ResponseDto<>(true, message, 200, LocalDateTime.now(), null, data);
    }

    public static <T> ResponseDto<T> error(ErrorCode errorInfo) {
        return new ResponseDto<>(false, errorInfo.getMessage(), errorInfo.getSatusCode(), LocalDateTime.now(),
                                 errorInfo.getErrorCode(), null);
    }

    public static <T> ResponseDto<T> error(ErrorCode errorInfo, String message) {
        return new ResponseDto<>(false, errorInfo.getMessage() + " [ " + message + " ]", errorInfo.getSatusCode(),
                                 LocalDateTime.now(), errorInfo.getErrorCode(), null);
    }
}