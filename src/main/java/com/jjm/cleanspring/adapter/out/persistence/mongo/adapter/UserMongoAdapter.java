package com.jjm.cleanspring.adapter.out.persistence.mongo.adapter;

import com.jjm.cleanspring.adapter.out.persistence.mongo.entity.UserMongoEntity;
import com.jjm.cleanspring.adapter.out.persistence.mongo.mapper.UserMapper;
import com.jjm.cleanspring.adapter.out.persistence.mongo.repository.UserMongoRepository;
import com.jjm.cleanspring.application.port.out.user.UserPort;
import com.jjm.cleanspring.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMongoAdapter implements UserPort {
    private final UserMongoRepository repository;
    private final UserMapper mapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public User saveUser(User user) {
        UserMongoEntity entity = mapper.toEntity(user);
        UserMongoEntity savedEntity = repository.save(entity);

        return mapper.toUser(savedEntity);
    }

    @Override
    public User findByUserName(String userName) {
        Optional<UserMongoEntity> entity = repository.findByUserName(userName);

        if (entity.isEmpty()) {
            throw new NoSuchElementException("해당 유저는 존재하지 않습니다.");
        }

        return mapper.toUser(entity.get());
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

    @Override
    public void updateUser(User user) {
        if (repository.findById(user.getId())
                      .isEmpty()) {
            throw new NoSuchElementException("해당 유저는 존재하지 않습니다.");
        }

        Query query = new Query(Criteria.where("_id")
                                        .is(user.getId()));
        Update update = new Update().set("name", user.getName())
                                    .set("email", user.getEmail())
                                    .set("updated_at", user.getUpdatedAt());

        mongoTemplate.updateFirst(query, update, UserMongoEntity.class);
    }
}
