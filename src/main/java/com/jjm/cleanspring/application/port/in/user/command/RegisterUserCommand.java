package com.jjm.cleanspring.application.port.in.user.command;

import com.jjm.cleanspring.domain.User;
import com.jjm.cleanspring.infrastructure.abstraction.SelfValidating;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RegisterUserCommand extends SelfValidating<RegisterUserCommand> {
    @Pattern(regexp = "^\\S+$", message = "공백이 포함되면 안됩니다.")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "확인 비밀번호는 필수 입력 값입니다.")
    private String confirmPassword;

    @Email(message = "올바른 이메일 형식에 맞게 입력해주세요.")
    private String email;

    @Builder
    public RegisterUserCommand(String userName, String password, String confirmPassword, String email) {
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.validateSelf();
    }

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
