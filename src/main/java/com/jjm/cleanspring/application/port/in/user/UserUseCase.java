package com.jjm.cleanspring.application.port.in.user;

import com.jjm.cleanspring.application.port.in.user.command.RegisterUserCommand;
import com.jjm.cleanspring.application.port.in.user.command.UpdateUserCommand;
import com.jjm.cleanspring.domain.User;

import java.util.List;

public interface UserUseCase {
    User registerUser(RegisterUserCommand command);

    List<User> getAllUser();

    void deleteUser(Long id);

    void updateUser(UpdateUserCommand command);
}
