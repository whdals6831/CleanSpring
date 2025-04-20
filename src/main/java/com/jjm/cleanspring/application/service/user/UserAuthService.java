package com.jjm.cleanspring.application.service.user;

import com.jjm.cleanspring.application.port.in.user.UserAuthUseCase;
import com.jjm.cleanspring.application.port.in.user.command.LoginUserCommand;
import com.jjm.cleanspring.application.port.out.user.UserPort;
import com.jjm.cleanspring.application.port.out.user.token.RefreshTokenPort;
import com.jjm.cleanspring.application.service.user.token.JwtProvider;
import com.jjm.cleanspring.domain.User;
import com.jjm.cleanspring.domain.token.JwtToken;
import com.jjm.cleanspring.domain.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserAuthUseCase {
    private final UserPort userPort;
    private final RefreshTokenPort refreshTokenPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public JwtToken login(LoginUserCommand command) {
        // 사용자 조회
        User user = userPort.findByUserName(command.getUserName());

        if (user == null) {
            throw new NoSuchElementException("해당 이름의 사용자를 찾을 수 없습니다.");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(command.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        // JWT 토큰 생성
        String accessToken = jwtProvider.generateToken(user.getId()
                                                           .toString(),
                                                       user.getUserName());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId()
                                                                   .toString(),
                                                               user.getUserName());

        // 생성할 리프레시 토큰
        RefreshToken createToken = RefreshToken.builder()
                                               .userId(user.getId())
                                               .token(refreshToken)
                                               .expirationDate(jwtProvider.extractExpiredDate(refreshToken))
                                               .deviceInfo("내 컴퓨터")
                                               .ipAddress("127.0.0.1")
                                               .build();

        // 리프레시 토큰 저장
        refreshTokenPort.createRefreshToken(createToken);

        return JwtToken.builder()
                       .userId(user.getId()
                                   .toString())
                       .accessToken(accessToken)
                       .refreshToken(refreshToken)
                       .build();
    }
}
