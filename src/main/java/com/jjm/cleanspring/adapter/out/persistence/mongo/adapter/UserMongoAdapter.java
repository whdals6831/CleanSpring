package com.jjm.cleanspring.adapter.out.persistence.mongo.adapter;

import com.jjm.cleanspring.adapter.out.persistence.mongo.entity.UserMongoEntity;
import com.jjm.cleanspring.adapter.out.persistence.mongo.mapper.UserMapper;
import com.jjm.cleanspring.adapter.out.persistence.mongo.repository.UserMongoRepository;
import com.jjm.cleanspring.application.port.out.user.UserPort;
import com.jjm.cleanspring.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public void deleteUser(String id) {
        if (repository.findById(id)
                      .isEmpty()) {
            throw new NoSuchElementException("해당 유저는 존재하지 않습니다.");
        }
        repository.deleteById(id);
    }
}
