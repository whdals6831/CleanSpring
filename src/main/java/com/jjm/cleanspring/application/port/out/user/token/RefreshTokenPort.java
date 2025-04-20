package com.jjm.cleanspring.application.port.out.user.token;

import com.jjm.cleanspring.domain.token.RefreshToken;

public interface RefreshTokenPort {
    /**
     * 새 리프레시 토큰 생성
     *
     * @param refreshToken 리프레시 토큰
     * @return 생성된 RefreshToken 객체
     */
    RefreshToken createRefreshToken(RefreshToken refreshToken);

    /**
     * 리프레시 토큰 찾기
     *
     * @param token 토큰 문자열
     * @return 발견한 리프레시 토큰
     */
    RefreshToken findByToken(String token);

    /**
     * 리프레시 토큰 사용 시간 업데이트
     *
     * @param token 토큰 문자열
     * @return 업데이트한 리프레시 토큰
     */
    RefreshToken updateTokenUsage(String token);

    /**
     * 리프레시 토큰 취소
     *
     * @param token 토큰 문자열
     * @return 성공 여부
     */
    boolean revokeToken(String token);

    /**
     * 사용자의 모든 리프레시 토큰 취소
     *
     * @param userId 사용자 아이디
     * @return 취소된 토큰 수
     */
    int revokeAllUserTokens(Long userId);

    /**
     * 만료된 리프레시 토큰 정리
     * (현재 시간 기준)
     *
     * @return 정리된 토큰 수
     */
    int cleanupExpiredTokens();
}
