package com.jjm.cleanspring.adapter.in.web.user;

import com.jjm.cleanspring.adapter.in.web.dto.user.FindUserResponse;
import com.jjm.cleanspring.adapter.in.web.dto.user.RegisterUserRequest;
import com.jjm.cleanspring.adapter.in.web.dto.user.RegisterUserResponse;
import com.jjm.cleanspring.adapter.out.persistence.mongo.mapper.UserMapper;
import com.jjm.cleanspring.application.port.in.user.command.RegisterUserCommand;
import com.jjm.cleanspring.application.port.in.user.usecase.UserUseCase;
import com.jjm.cleanspring.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "유저 관련 API")
public class UserController {
    private final UserUseCase userUseCase;
    private final UserMapper userMapper;

    @PostMapping
    @Operation(summary = "사용자 등록", description = "사용자를 등록합니다.")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = RegisterUserCommand.builder()
                .userName(request.userName())
                .password(request.password())
                .confirmPassword(request.confirmPassword())
                .email(request.email())
                .build();

        User user = userUseCase.registerUser(command);
        RegisterUserResponse response = userMapper.toRegisterUserResponse(user);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "모든 사용자 목록", description = "모든 사용자 목록을 가져옵니다.")
    public ResponseEntity<List<FindUserResponse>> findAllUser() {
        List<User> userList = userUseCase.getAllUser();
        List<FindUserResponse> response = userMapper.toFindUserResponseList(userList);

        return ResponseEntity.ok(response);
    }
}
