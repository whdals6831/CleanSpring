package com.jjm.cleanspring.adapter.out.persistence.mongo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMongoEntity {
    @Id
    private String id;
    private String userName;
    private String password;
    private String email;
    private LocalDateTime createdAt;
}
