package com.jjm.cleanspring.adapter.in.web.dto.user;

public record UpdateUserRequest(Long id,
                                String name,
                                String email) {
}
