package com.jjm.cleanspring.infrastructure.config.security.service;

import com.jjm.cleanspring.application.port.out.user.UserPort;
import com.jjm.cleanspring.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserPort userPort;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = userPort.findByUserName(userName);

        return new CustomUserDetails(user.getId(), user.getUserName());
    }
}
