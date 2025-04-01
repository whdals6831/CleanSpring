package com.jjm.cleanspring.adapter.out.persistence.mongo.mapper;

import com.jjm.cleanspring.adapter.out.persistence.mongo.entity.UserMongoEntity;
import com.jjm.cleanspring.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toUser(UserMongoEntity entity);

    UserMongoEntity toEntity(User user);

    List<User> toUserList(List<UserMongoEntity> entityList);
}
