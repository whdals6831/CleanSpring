package com.jjm.cleanspring.application.port.in.user.token;

import com.jjm.cleanspring.domain.token.JwtToken;

public interface RefreshTokenUseCase {
    JwtToken generateAccessToken(String refreshTokenHeader);
}
