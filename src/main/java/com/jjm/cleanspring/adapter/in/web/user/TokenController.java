package com.jjm.cleanspring.adapter.in.web.user;

import com.jjm.cleanspring.adapter.in.web.dto.ResponseDto;
import com.jjm.cleanspring.application.port.in.user.token.RefreshTokenUseCase;
import com.jjm.cleanspring.domain.token.JwtToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Token API", description = "토큰 재발급 관련 API")
public class TokenController {
    private final RefreshTokenUseCase refreshTokenUseCase;

    @Operation(summary = "새 액세스 토큰 발급", description = "유효한 리프레시 토큰으로 새 액세스 토큰을 발급합니다.")
    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDto<JwtToken>> issueAccessToken(@RequestHeader("Authorization") String refreshTokenHeader) {
        JwtToken jwtToken = refreshTokenUseCase.generateAccessToken(refreshTokenHeader);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(ResponseDto.success(jwtToken));
    }
}
