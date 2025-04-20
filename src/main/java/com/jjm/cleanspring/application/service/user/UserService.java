package com.jjm.cleanspring.application.service.user;

import com.jjm.cleanspring.application.port.in.user.UserUseCase;
import com.jjm.cleanspring.application.port.in.user.command.RegisterUserCommand;
import com.jjm.cleanspring.application.port.in.user.command.UpdateUserCommand;
import com.jjm.cleanspring.application.port.out.user.UserPort;
import com.jjm.cleanspring.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService implements UserUseCase {
    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterUserCommand command) {
        if (userPort.existsByUserName(command.getUserName())) {
            throw new IllegalArgumentException("해당 이름을 가진 유저가 이미 존재합니다.");
        }

        if (!Objects.equals(command.getPassword(), command.getConfirmPassword())) {
            throw new IllegalArgumentException("입력한 비밀번호가 서로 일치하지 않습니다.");
        }

        // 비밀번호 암호화
        String passwordHash = passwordEncoder.encode(command.getPassword());

        User user = command.toUser(passwordHash);

        return userPort.saveUser(user);
    }

    @Override
    public List<User> getAllUser() {
        return userPort.findAllUser();
    }

    @Override
    public void deleteUser(Long id) {
        if (!userPort.existsById(id)) {
            throw new NoSuchElementException("해당 유저는 존재하지 않습니다.");
        }

        userPort.deleteUser(id);
    }

    @Override
    public void updateUser(UpdateUserCommand command) {
        User user = command.toUser();
        User storedUser = userPort.findById(user.getId());

        if (storedUser == null) {
            throw new NoSuchElementException("해당 유저는 존재하지 않습니다.");
        }

        // 업데이트 할 수 있는 모든 필드 갱신
        storedUser.setName(user.getName());
        storedUser.setEmail(user.getEmail());
        storedUser.setUpdatedAt(LocalDateTime.now());

        userPort.updateUser(user);
    }
}
