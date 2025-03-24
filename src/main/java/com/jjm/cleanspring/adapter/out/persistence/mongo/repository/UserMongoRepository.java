package com.jjm.cleanspring.adapter.out.persistence.mongo.repository;

import com.jjm.cleanspring.adapter.out.persistence.mongo.entity.UserMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserMongoEntity, String> {
}
