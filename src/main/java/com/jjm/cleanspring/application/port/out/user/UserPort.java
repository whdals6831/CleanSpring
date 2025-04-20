package com.jjm.cleanspring.application.port.out.user;

import com.jjm.cleanspring.domain.User;

import java.util.List;

public interface UserPort {
    User saveUser(User user);

    User findById(Long id);

    User findByUserName(String userName);

    boolean existsById(Long id);

    boolean existsByUserName(String userName);

    List<User> findAllUser();

    void deleteUser(Long id);

    User updateUser(User user);
}
