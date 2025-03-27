package com.jjm.cleanspring.application.port.in.user.usecase;

import com.jjm.cleanspring.application.port.in.user.command.RegisterUserCommand;
import com.jjm.cleanspring.domain.User;

import java.util.List;

public interface UserUseCase {
    User registerUser(RegisterUserCommand command);

    List<User> getAllUser();

    void deleteUser(String id);
}
