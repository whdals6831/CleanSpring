package com.jjm.cleanspring.adapter.out.persistence.postgres.mapper;

import com.jjm.cleanspring.adapter.out.persistence.postgres.entity.UserEntity;
import com.jjm.cleanspring.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);

    List<User> toDomainList(List<UserEntity> entityList);

    List<UserEntity> toEntityList(List<User> domainList);
}
