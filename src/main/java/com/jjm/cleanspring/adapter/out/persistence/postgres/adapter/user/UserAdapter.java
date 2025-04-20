package com.jjm.cleanspring.adapter.out.persistence.postgres.adapter.user;

import com.jjm.cleanspring.adapter.out.persistence.postgres.entity.UserEntity;
import com.jjm.cleanspring.adapter.out.persistence.postgres.mapper.UserMapper;
import com.jjm.cleanspring.adapter.out.persistence.postgres.repository.UserRepository;
import com.jjm.cleanspring.application.port.out.user.UserPort;
import com.jjm.cleanspring.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPort {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User saveUser(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = userRepository.save(entity);

        return userMapper.toDomain(savedEntity);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                             .map(userMapper::toDomain)
                             .orElse(null);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                             .map(userMapper::toDomain)
                             .orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public List<User> findAllUser() {
        return userMapper.toDomainList(userRepository.findAll());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        UserEntity storedUser = userRepository.findById(user.getId())
                                              .orElse(null);

        if (storedUser == null) {
            throw new NoSuchElementException("User not found: " + user.getId());
        }

        // 수정할 수 있는 필드 값 갱신
        storedUser.setName(user.getName());
        storedUser.setEmail(user.getEmail());

        UserEntity savedEntity = userRepository.save(storedUser);
        return userMapper.toDomain(savedEntity);
    }
}
