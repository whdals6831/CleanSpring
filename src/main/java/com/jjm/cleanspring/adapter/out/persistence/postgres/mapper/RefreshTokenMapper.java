package com.jjm.cleanspring.adapter.out.persistence.postgres.mapper;

import com.jjm.cleanspring.adapter.out.persistence.postgres.entity.RefreshTokenEntity;
import com.jjm.cleanspring.domain.token.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RefreshTokenMapper {
    @Mapping(target = "userId", source = "user.id")
    RefreshToken toDomain(RefreshTokenEntity entity);

    RefreshTokenEntity toEntity(RefreshToken domain);
}
