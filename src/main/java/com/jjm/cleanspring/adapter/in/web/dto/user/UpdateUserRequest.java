package com.jjm.cleanspring.adapter.in.web.dto.user;

public record UpdateUserRequest(String id,
                                String name,
                                String email) {
}
