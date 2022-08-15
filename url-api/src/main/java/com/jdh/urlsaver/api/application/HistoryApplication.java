package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.repository.SignInHistoryRepository;
import com.jdh.urlsaver.api.repository.SignUpHistoryRepository;
import com.jdh.urlsaver.api.service.dto.User;
import com.jdh.urlsaver.common.exception.InvalidInputException;
import com.jdh.urlsaver.model.entity.auth.SignUpHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class HistoryApplication {

    private final SignInHistoryRepository signInHistoryRepository;
    private final SignUpHistoryRepository signUpHistoryRepository;

    @Transactional
    public void signIn(User user) {
    }

    @Transactional
    public void leaveSignUpEvent(User user, String emailCode) {
        signUpHistoryRepository.save(SignUpHistoryEntity.builder()
                                                        .loginId(user.getLoginId())
                                                        .keyValue(String.valueOf(user.getUserId()))
                                                        .emailCode(emailCode)
                                                        .build());
    }

    @Transactional(readOnly = true)
    public void validateEmail(String userId, String code) {
        SignUpHistoryEntity historyEntity = signUpHistoryRepository.findByKeyValue(userId);

        // TODO: 2022/08/15 add expired time
        if (!historyEntity.getEmailCode().equals(code)) {
            throw new InvalidInputException(
                    String.format("failed to verify email, userId: %s, code: %s", userId, code));
        }
    }
}
