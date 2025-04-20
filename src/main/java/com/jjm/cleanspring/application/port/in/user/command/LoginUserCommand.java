package com.jjm.cleanspring.application.port.in.user.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginUserCommand {
    @Pattern(regexp = "^\\S+$", message = "공백이 포함되면 안됩니다.")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Builder
    public LoginUserCommand(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
