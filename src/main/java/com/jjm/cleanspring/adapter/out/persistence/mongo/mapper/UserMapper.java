package com.jjm.cleanspring.adapter.out.persistence.mongo.mapper;


import com.jjm.cleanspring.adapter.in.web.dto.user.FindUserResponse;
import com.jjm.cleanspring.adapter.in.web.dto.user.RegisterUserResponse;
import com.jjm.cleanspring.adapter.out.persistence.mongo.entity.UserMongoEntity;
import com.jjm.cleanspring.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    RegisterUserResponse toRegisterUserResponse(User user);

    UserMongoEntity toUserMongoEntity(User user);

    User toUser(UserMongoEntity entity);

    List<User> toUserList(List<UserMongoEntity> entityList);

    List<FindUserResponse> toFindUserResponseList(List<User> userList);
}
