package com.jjm.cleanspring.adapter.in.web.dto.user;

public record RegisterUserRequest(String userName,
                                  String password,
                                  String confirmPassword,
                                  String email) {
}
