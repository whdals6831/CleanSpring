package com.jjm.cleanspring.adapter.out.persistence.postgres.repository;

import com.jjm.cleanspring.adapter.out.persistence.postgres.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
}
