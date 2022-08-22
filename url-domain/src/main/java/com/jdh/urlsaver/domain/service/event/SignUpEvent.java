package com.jdh.urlsaver.domain.service.event;

import com.jdh.urlsaver.domain.model.entity.user.User;
import org.springframework.context.ApplicationEvent;

public class SignUpEvent extends ApplicationEvent {

    private final User user;

    public SignUpEvent(Object object, User user) {
        super(object);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}