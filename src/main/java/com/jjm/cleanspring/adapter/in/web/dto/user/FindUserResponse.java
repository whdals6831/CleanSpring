package com.jjm.cleanspring.adapter.in.web.dto.user;

import java.time.LocalDateTime;

public record FindUserResponse(String id, String userName, String email, LocalDateTime createdAt) {
}
