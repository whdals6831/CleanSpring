package com.jjm.cleanspring.adapter.out.persistence.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;
}
