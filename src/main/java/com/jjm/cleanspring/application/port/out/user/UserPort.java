package com.jjm.cleanspring.application.port.out.user;

import com.jjm.cleanspring.domain.User;

import java.util.List;

public interface UserPort {
    User saveUser(User user);

    User findByUserName(String userName);

    List<User> findAllUser();

    void deleteUser(String id);

    void updateUser(User user);
}
