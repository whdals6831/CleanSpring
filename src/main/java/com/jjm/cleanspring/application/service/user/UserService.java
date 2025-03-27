package com.jjm.cleanspring.application.service.user;

import com.jjm.cleanspring.application.port.in.user.command.RegisterUserCommand;
import com.jjm.cleanspring.application.port.in.user.usecase.UserUseCase;
import com.jjm.cleanspring.application.port.out.user.UserPort;
import com.jjm.cleanspring.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService implements UserUseCase {
    private final UserPort userPort;

    @Override
    public User registerUser(RegisterUserCommand command) {
        if (!Objects.equals(command.getPassword(), command.getConfirmPassword())) {
            throw new IllegalArgumentException("입력한 비밀번호가 서로 일치하지 않습니다.");
        }

        User user = command.toEntity();

        return userPort.saveUser(user);
    }

    @Override
    public List<User> getAllUser() {
        return userPort.findAllUser();
    }

    @Override
    public void deleteUser(String id) {
        userPort.deleteUser(id);
    }
}
