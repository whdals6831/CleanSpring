package com.jjm.cleanspring.adapter.in.web.user;

import com.jjm.cleanspring.adapter.in.web.dto.ResponseDto;
import com.jjm.cleanspring.adapter.in.web.dto.user.RegisterUserRequest;
import com.jjm.cleanspring.adapter.in.web.dto.user.UpdateUserRequest;
import com.jjm.cleanspring.adapter.in.web.dto.user.UserResponse;
import com.jjm.cleanspring.application.port.in.user.UserUseCase;
import com.jjm.cleanspring.application.port.in.user.command.RegisterUserCommand;
import com.jjm.cleanspring.application.port.in.user.command.UpdateUserCommand;
import com.jjm.cleanspring.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관련 API")
public class UserController {
    private final UserUseCase userUseCase;

    @Operation(summary = "사용자 등록", description = "사용자를 등록합니다.")
    @PostMapping("/register")
    public ResponseEntity<ResponseDto<UserResponse>> registerUser(@RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = RegisterUserCommand.builder()
                                                         .userName(request.userName())
                                                         .password(request.password())
                                                         .confirmPassword(request.confirmPassword())
                                                         .name(request.name())
                                                         .email(request.email())
                                                         .build();

        User user = userUseCase.registerUser(command);
        UserResponse response = UserResponse.toDto(user);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(ResponseDto.success(response));
    }

    @Operation(summary = "사용자 목록", description = "사용자 목록을 가져옵니다.")
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<UserResponse>>> getAllUser() {
        List<User> userList = userUseCase.getAllUser();
        List<UserResponse> response = UserResponse.toDtoList(userList);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(ResponseDto.success(response));
    }

    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Object>> deleteUser(@PathVariable Long id) {
        userUseCase.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(ResponseDto.success());
    }

    @Operation(summary = "사용자 수정", description = "사용자의 정보를 수정합니다.")
    @PostMapping("/update")
    public ResponseEntity<ResponseDto<UserResponse>> updateUser(@RequestBody UpdateUserRequest request) {
        UpdateUserCommand command = UpdateUserCommand.builder()
                                                     .id(request.id())
                                                     .name(request.name())
                                                     .email(request.email())
                                                     .build();

        User savedUser = userUseCase.updateUser(command);
        UserResponse response = UserResponse.toDto(savedUser);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(ResponseDto.success(response));
    }
}
