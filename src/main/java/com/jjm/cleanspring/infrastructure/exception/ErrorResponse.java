package com.jjm.cleanspring.infrastructure.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private HttpStatus status;
    private String code;
    private String message;
}