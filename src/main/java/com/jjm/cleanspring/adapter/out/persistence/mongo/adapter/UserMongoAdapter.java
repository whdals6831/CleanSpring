package com.jjm.cleanspring.adapter.out.persistence.mongo.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMongoAdapter {
//    private final UserMongoRepository repository;
//    private final UserMapper mapper;
//    private final MongoTemplate mongoTemplate;
//
//    @Override
//    public User saveUser(User user) {
//        UserMongoEntity entity = mapper.toEntity(user);
//        UserMongoEntity savedEntity = repository.save(entity);
//
//        return mapper.toUser(savedEntity);
//    }
//
//    @Override
//    public Optional<User> findById(String id) {
//        return repository.findById(id)
//                         .map(mapper::toUser);
//    }
//
//    @Override
//    public Optional<User> findByUserName(String userName) {
//        return repository.findByUserName(userName)
//                         .map(mapper::toUser);
//    }
//
//    @Override
//    public List<User> findAllUser() {
//        List<UserMongoEntity> findAllUserEntity = repository.findAll();
//
//        return mapper.toUserList(findAllUserEntity);
//    }
//
//    @Override
//    public void deleteUser(String id) {
//        repository.deleteById(id);
//    }
//
//    @Override
//    public void updateUser(User user) {
//        Query query = new Query(Criteria.where("_id")
//                                        .is(user.getId()));
//        Update update = new Update().set("name", user.getName())
//                                    .set("email", user.getEmail())
//                                    .set("updated_at", user.getUpdatedAt());
//
//        mongoTemplate.updateFirst(query, update, UserMongoEntity.class);
//    }
}
