package com.jdh.urlsaver.api.service;

import com.jdh.urlsaver.api.controller.dto.SignUpRequest;
import com.jdh.urlsaver.api.repository.SignUpHistoryRepository;
import com.jdh.urlsaver.api.repository.UserRepository;
import com.jdh.urlsaver.domain.common.exception.InvalidInputException;
import com.jdh.urlsaver.domain.common.exception.UnauthorizedException;
import com.jdh.urlsaver.domain.model.entity.user.ProviderType;
import com.jdh.urlsaver.domain.model.entity.user.RoleType;
import com.jdh.urlsaver.domain.model.entity.user.User;
import com.jdh.urlsaver.domain.model.entity.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class AccountService {

    private final UserRepository userRepository;
    private final SignUpHistoryRepository signUpHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(SignUpRequest signUpRequest) {
        UserEntity existingUser = userRepository.findByLoginId(signUpRequest.getLoginId()).orElse(null);
        Long userId = null;
        if (existingUser != null) {
            if (existingUser.isEmailVerified()) {
                throw new UnauthorizedException("failed to sign up, loginId: " + signUpRequest.getLoginId());
            } else {
                userId = existingUser.getUserId();
            }
        }
        String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        UserEntity userEntity = UserEntity.builder()
                                          .userId(userId)
                                          .loginId(signUpRequest.getLoginId())
                                          .hashedPassword(hashedPassword)
                                          .email(signUpRequest.getEmail())
                                          .familyName(signUpRequest.getFamilyName())
                                          .givenName(signUpRequest.getGivenName())
                                          .emailVerified(false)
                                          .providerType(ProviderType.LOCAL)
                                          .roleType(RoleType.USER)
                                          .dormant(false)
                                          .dormantAt(null)
                                          .withdrawn(false)
                                          .build();
        UserEntity savedUser = userRepository.save(userEntity);
        return User.of(savedUser);
    }

    @Transactional
    public void successEmailVerification(Long userId) {
        UserEntity existingUser = userRepository.findByUserId(userId).orElseThrow(
                () -> new InvalidInputException(String.format("failed to verify email, userId : %s", userId)));
        existingUser.setEmailVerified(true);
        userRepository.save(existingUser);
    }

    @Transactional(readOnly = true)
    public User findUser(Long userId) {
        return User.of(userRepository.findByUserId(userId).orElseThrow(
                () -> new InvalidInputException(String.format("failed to find user, userIdL: %s", userId))));
    }
}
