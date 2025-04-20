package com.jjm.cleanspring.adapter.out.persistence.postgres.repository;

import com.jjm.cleanspring.adapter.out.persistence.postgres.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
