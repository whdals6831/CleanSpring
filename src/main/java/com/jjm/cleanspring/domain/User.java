package com.jjm.cleanspring.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private Long id;
    private String name;                  // 사용자 이름
    private String userName;              // 아이디
    private String passwordHash;          // 비밀번호 (암호화)
    private String email;                 // 이메일
    private LocalDateTime createdAt;      // 계정 생성 시간
    private LocalDateTime updatedAt;      // 정보 수정 시간
}