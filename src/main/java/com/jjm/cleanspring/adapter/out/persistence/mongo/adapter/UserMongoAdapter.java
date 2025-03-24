package com.jjm.cleanspring.adapter.out.persistence.mongo.adapter;

import com.jjm.cleanspring.adapter.out.persistence.mongo.entity.UserMongoEntity;
import com.jjm.cleanspring.adapter.out.persistence.mongo.mapper.UserMapper;
import com.jjm.cleanspring.adapter.out.persistence.mongo.repository.UserMongoRepository;
import com.jjm.cleanspring.application.port.out.user.UserPort;
import com.jjm.cleanspring.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMongoAdapter implements UserPort {
    private final UserMongoRepository repository;
    private final UserMapper mapper;

    @Override
    public User saveUser(User user) {
        UserMongoEntity entity = mapper.toUserMongoEntity(user);
        UserMongoEntity savedEntity = repository.save(entity);

        return mapper.toUser(savedEntity);
    }

    @Override
    public List<User> findAllUser() {
        List<UserMongoEntity> findAllUserEntity = repository.findAll();

        return mapper.toUserList(findAllUserEntity);
    }
}
