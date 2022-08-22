package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.controller.dto.SignUpRequest;
import com.jdh.urlsaver.api.service.AccountService;
import com.jdh.urlsaver.domain.model.entity.user.User;
import com.jdh.urlsaver.domain.service.event.EventService;
import com.jdh.urlsaver.domain.service.event.NewUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountApplication {

    private final AccountService accountService;
    private final EventService eventService;

    public User register(SignUpRequest signUpRequest) {
        User user = accountService.register(signUpRequest);
        eventService.publish(new NewUserEvent(this, user));
        return user;
    }

    public void successEmailVerification(Long userId) {
        accountService.successEmailVerification(userId);
    }

    public User findUser(Long userId) {
        return accountService.findUser(userId);
    }
}
