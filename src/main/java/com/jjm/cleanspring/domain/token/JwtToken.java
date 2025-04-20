package com.jjm.cleanspring.domain.token;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtToken {
    private String userId;
    private String accessToken;
    private String refreshToken;
}
