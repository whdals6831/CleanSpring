package com.jjm.cleanspring.adapter.in.web.dto.user;

import com.jjm.cleanspring.domain.User;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(Long id,
                           String user,
                           String userName,
                           String email,
                           LocalDateTime createdAt) {
    public static UserResponse toDto(User user) {
        return new UserResponse(user.getId(),
                                user.getName(),
                                user.getUserName(),
                                user.getEmail(),
                                user.getCreatedAt());
    }

    public static List<UserResponse> toDtoList(List<User> userList) {
        return userList.stream()
                       .map(UserResponse::toDto)
                       .toList();
    }
}
