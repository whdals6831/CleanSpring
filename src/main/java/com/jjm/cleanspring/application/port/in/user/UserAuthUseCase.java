package com.jjm.cleanspring.application.port.in.user;

import com.jjm.cleanspring.application.port.in.user.command.LoginUserCommand;
import com.jjm.cleanspring.domain.token.JwtToken;

public interface UserAuthUseCase {
    JwtToken login(LoginUserCommand command);
}
