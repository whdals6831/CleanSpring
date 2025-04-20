package com.jjm.cleanspring.adapter.in.web.user;

import com.jjm.cleanspring.adapter.in.web.dto.ResponseDto;
import com.jjm.cleanspring.adapter.in.web.dto.user.LoginRequest;
import com.jjm.cleanspring.application.port.in.user.UserAuthUseCase;
import com.jjm.cleanspring.application.port.in.user.command.LoginUserCommand;
import com.jjm.cleanspring.domain.token.JwtToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "사용자 인증 관련 API")
public class AuthController {
    private final UserAuthUseCase userAuthUseCase;

    @Operation(summary = "사용자 로그인", description = "로그인 후 JWT 토큰을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<JwtToken>> login(@RequestBody LoginRequest loginRequest) {
        LoginUserCommand command = LoginUserCommand.builder()
                                                   .userName(loginRequest.userName())
                                                   .password(loginRequest.password())
                                                   .build();

        JwtToken jwtToken = userAuthUseCase.login(command);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(ResponseDto.success(jwtToken));
    }
}
