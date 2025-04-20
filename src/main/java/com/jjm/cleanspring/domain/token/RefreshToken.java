package com.jjm.cleanspring.domain.token;

import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
    @Id
    private Long id;
    private Long userId;                     // 사용자 ID
    private String token;                    // 리프레시 토큰 값
    private String deviceInfo;               // 디바이스 정보 (선택)
    private String ipAddress;                // IP 주소 (선택)
    private LocalDateTime expirationDate;    // 만료 시간
    private boolean revoked;                 // 토큰 취소 여부

    // 토큰이 유효한지 확인
    public boolean isValid() {
        return !revoked && expirationDate.isAfter(LocalDateTime.now());
    }

    // 토큰 취소
    public void Revoke() {
        revoked = true;
    }
}
