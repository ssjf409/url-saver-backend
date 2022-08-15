package com.jdh.urlsaver.api.service;

import com.jdh.urlsaver.api.application.dto.SignUpRequestDto;
import com.jdh.urlsaver.api.repository.SignUpHistoryRepository;
import com.jdh.urlsaver.api.repository.UserRepository;
import com.jdh.urlsaver.api.service.dto.User;
import com.jdh.urlsaver.common.exception.InvalidInputException;
import com.jdh.urlsaver.common.exception.UnauthorizedException;
import com.jdh.urlsaver.model.entity.user.ProviderType;
import com.jdh.urlsaver.model.entity.user.RoleType;
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
    private final SignUpHistoryRepository signUpHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User signIn(@NotNull String loginId, @NotNull String password) {
        UserEntity userEntity = userRepository.findByLoginId(loginId);
        if (userEntity == null) {
            throw new UnauthorizedException("failed to find sign in, loginId: " + loginId);
        } else if (!passwordEncoder.matches(password, userEntity.getHashedPassword())) {
            throw new UnauthorizedException("failed to sign in, loginId: " + loginId);
        }
        return User.convert(userEntity);
    }

    @Transactional
    public User register(SignUpRequestDto signUpRequestDto) {
        UserEntity existingUser = userRepository.findByLoginId(signUpRequestDto.getLoginId());
        Long userId = null;
        if (existingUser != null) {
            if (existingUser.isEmailVerified()) {
                throw new UnauthorizedException("failed to sign up, loginId: " + signUpRequestDto.getLoginId());
            } else {
                userId = existingUser.getUserId();
            }
        }
        String hashedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        UserEntity userEntity = UserEntity.builder()
                                          .userId(userId)
                                          .loginId(signUpRequestDto.getLoginId())
                                          .hashedPassword(hashedPassword)
                                          .email(signUpRequestDto.getEmail())
                                          .familyName(signUpRequestDto.getFamilyName())
                                          .givenName(signUpRequestDto.getGivenName())
                                          .emailVerified(false)
                                          .providerType(ProviderType.LOCAL)
                                          .roleType(RoleType.USER)
                                          .dormant(false)
                                          .dormantAt(null)
                                          .withdrawn(false)
                                          .build();
        UserEntity savedUser = userRepository.save(userEntity);
        return User.convert(savedUser);
    }

    @Transactional
    public void successEmailVerification(Long userId) {
        UserEntity existingUser = userRepository.findByUserId(userId);
        if (existingUser == null) {
            throw new InvalidInputException(String.format("failed to verify email, userId : %s", userId));
        }
        existingUser.setEmailVerified(true);
        userRepository.save(existingUser);
    }
}
