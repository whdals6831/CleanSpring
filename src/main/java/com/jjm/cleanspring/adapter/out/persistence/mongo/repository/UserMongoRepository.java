package com.jjm.cleanspring.adapter.out.persistence.mongo.repository;

import com.jjm.cleanspring.adapter.out.persistence.mongo.entity.UserMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserMongoEntity, String> {
    Optional<UserMongoEntity> findByUserName(String userName);
}