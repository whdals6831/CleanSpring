package com.jjm.cleanspring.application.port.in.user.command;

import com.jjm.cleanspring.application.port.in.SelfValidating;
import com.jjm.cleanspring.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterUserCommand extends SelfValidating<RegisterUserCommand> {
    @Pattern(regexp = "^\\S+$", message = "공백이 포함되면 안됩니다.")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "확인 비밀번호는 필수 입력 값입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Email(message = "올바른 이메일 형식에 맞게 입력해주세요.")
    private String email;

    @Builder
    public RegisterUserCommand(String userName, String password, String confirmPassword, String name, String email) {
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.email = email;
        this.validateSelf();
    }

    public User toUser(String passwordHash) {
        return User.builder()
                   .userName(userName)
                   .passwordHash(passwordHash)
                   .name(name)
                   .email(email)
                   .build();
    }
}
