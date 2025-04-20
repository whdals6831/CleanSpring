package com.jjm.cleanspring.application.service.user.token;

import com.jjm.cleanspring.application.exception.JwtAuthenticationException;
import com.jjm.cleanspring.application.port.in.user.token.RefreshTokenUseCase;
import com.jjm.cleanspring.application.port.out.user.token.RefreshTokenPort;
import com.jjm.cleanspring.domain.token.JwtToken;
import com.jjm.cleanspring.domain.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenUseCase {
    private final RefreshTokenPort refreshTokenPort;
    private final JwtProvider jwtProvider;

    @Override
    public JwtToken generateAccessToken(String refreshTokenHeader) {
        // 리프레시 토큰 추출
        String refreshToken = extractTokenFromHeader(refreshTokenHeader);

        // 리프레시 토큰 유효성 검증
        validateRefreshToken(refreshToken);

        // 리프레시 토큰 사용 기록 업데이트
        refreshTokenPort.updateTokenUsage(refreshToken);

        // 리프레시 토큰에서 userId와 userName 추출
        String userId = jwtProvider.extractId(refreshToken);
        String userName = jwtProvider.extractUserName(refreshToken);

        // 새 액세스 토큰 생성
        String newAccessToken = jwtProvider.generateToken(userId, userName);

        // 응답 생성 (리프레시 토큰은 재사용)
        return JwtToken.builder()
                       .userId(userId)
                       .accessToken(newAccessToken)
                       .refreshToken(refreshToken)
                       .build();
    }

    /**
     * 헤더에서 토큰 추출
     */
    private String extractTokenFromHeader(String header) {
        if (header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        else {
            return header;
        }
    }

    /**
     * 리프레시 토큰 유효성 검증
     */
    private void validateRefreshToken(String refreshToken) {
        // 토큰 형식 및 서명 검증
        jwtProvider.validateToken(refreshToken);

        // DB에 저장된 토큰 확인
        RefreshToken storedToken = refreshTokenPort.findByToken(refreshToken);

        if (storedToken == null) {
            throw new JwtAuthenticationException("존재하지 않는 리프레시 토큰입니다.");
        }

        // 토큰 상태 확인 (취소 여부)
        if (storedToken.isRevoked()) {
            throw new JwtAuthenticationException("취소된 리프레시 토큰입니다.");
        }

        // 토큰 만료 확인
        if (storedToken.getExpirationDate()
                       .isBefore(LocalDateTime.now())) {
            throw new JwtAuthenticationException("만료된 리프레시 토큰입니다.");
        }
    }
}
