package com.jjm.cleanspring.application.port.out.user;

import com.jjm.cleanspring.domain.User;

import java.util.List;

public interface UserPort {
    User saveUser(User user);

    List<User> findAllUser();
}
