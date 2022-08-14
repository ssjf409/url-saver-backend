package com.jdh.urlsaver.api.service;

import com.jdh.urlsaver.api.repository.UserRepository;
import com.jdh.urlsaver.api.service.dto.User;
import com.jdh.urlsaver.common.exception.UnauthorizedException;
import com.jdh.urlsaver.model.entity.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public User login(@NotNull String loginId, @NotNull String password) {
        UserEntity userEntity = userRepository.findByLoginId(loginId);
        if (userEntity == null) {
            throw new UnauthorizedException("failed to find login, loginId: " + loginId);
        } else if (!bCryptPasswordEncoder.matches(password, userEntity.getHashedPassword())) {
            throw new UnauthorizedException("failed to login, loginId: " + loginId);
        }
        return User.convert(userEntity);
    }
}
