package com.jjm.cleanspring.adapter.out.persistence.postgres.adapter.user.token;

import com.jjm.cleanspring.adapter.out.persistence.postgres.entity.RefreshTokenEntity;
import com.jjm.cleanspring.adapter.out.persistence.postgres.entity.UserEntity;
import com.jjm.cleanspring.adapter.out.persistence.postgres.mapper.RefreshTokenMapper;
import com.jjm.cleanspring.adapter.out.persistence.postgres.repository.RefreshTokenRepository;
import com.jjm.cleanspring.adapter.out.persistence.postgres.repository.UserRepository;
import com.jjm.cleanspring.application.port.out.user.token.RefreshTokenPort;
import com.jjm.cleanspring.domain.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class RefreshTokenAdapter implements RefreshTokenPort {
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenMapper refreshTokenMapper;
    private final UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(RefreshToken refreshToken) {
        UserEntity userEntity = userRepository.findById(refreshToken.getUserId())
                                              .orElse(null);

        if (userEntity == null) {
            throw new NoSuchElementException("User not found: " + refreshToken.getUserId());
        }

        RefreshTokenEntity refreshTokenEntity = refreshTokenMapper.toEntity(refreshToken);
        refreshTokenEntity.setUser(userEntity);
        RefreshTokenEntity savedEntity = refreshTokenRepository.save(refreshTokenEntity);

        return refreshTokenMapper.toDomain(savedEntity);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                                     .map(refreshTokenMapper::toDomain)
                                     .orElse(null);
    }

    @Override
    public RefreshToken updateTokenUsage(String token) {
        RefreshTokenEntity storedEntity = refreshTokenRepository.findByToken(token)
                                                                .orElse(null);

        if (storedEntity == null) {
            throw new NoSuchElementException("Token not found: " + token);
        }

        // 업데이트 횟수 증가
        storedEntity.setUpdateCount(storedEntity.getUpdateCount() + 1);

        RefreshTokenEntity updatedEntity = refreshTokenRepository.save(storedEntity);
        return refreshTokenMapper.toDomain(updatedEntity);
    }

    @Override
    public boolean revokeToken(String token) {
        return false;
    }

    @Override
    public int revokeAllUserTokens(Long userId) {
        return 0;
    }

    @Override
    public int cleanupExpiredTokens() {
        return 0;
    }
}
