package com.jdh.urlsaver.api.service.event;

import com.jdh.urlsaver.api.application.CategoryApplication;
import com.jdh.urlsaver.api.application.HistoryApplication;
import com.jdh.urlsaver.api.application.NodeApplication;
import com.jdh.urlsaver.api.application.NoticeApplication;
import com.jdh.urlsaver.domain.model.entity.category.Category;
import com.jdh.urlsaver.domain.model.entity.user.User;
import com.jdh.urlsaver.domain.service.event.NewUserEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NewUserEventListener {

    private final CategoryApplication categoryApplication;
    private final NodeApplication nodeApplication;
    private final HistoryApplication historyApplication;
    private final NoticeApplication noticeApplication;

    //    @TransactionalEventListener
    @Async
    @EventListener
    public void onEvent(NewUserEvent event) {
        User user = event.getUser();

        Category category = categoryApplication.createDefaultCategory(user.getUserId());
        nodeApplication.createDefaultNode(user.getUserId(), category.getCategoryId());

        String emailCode = RandomStringUtils.randomAlphanumeric(20);
        historyApplication.leaveSignUpEvent(user, emailCode);
        noticeApplication.sendVerifyEMail(user.getEmail(), String.valueOf(user.getUserId()), emailCode);
    }
}