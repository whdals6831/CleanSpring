package com.jjm.cleanspring.application.port.in.user.command;

import com.jjm.cleanspring.application.port.in.SelfValidating;
import com.jjm.cleanspring.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserCommand extends SelfValidating<UpdateUserCommand> {
    @NotBlank(message = "id는 필수 입력 값입니다.")
    private String id;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Email(message = "올바른 이메일 형식에 맞게 입력해주세요.")
    private String email;

    @Builder
    public UpdateUserCommand(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.validateSelf();
    }

    public User toUser() {
        return User.builder()
                   .id(id)
                   .name(name)
                   .email(email)
                   .updatedAt(LocalDateTime.now())
                   .build();
    }
}
