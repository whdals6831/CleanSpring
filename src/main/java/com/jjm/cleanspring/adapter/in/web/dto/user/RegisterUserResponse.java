package com.jjm.cleanspring.adapter.in.web.dto.user;

import java.time.LocalDateTime;

public record RegisterUserResponse(String id, String userName, LocalDateTime createdAt) {
}
