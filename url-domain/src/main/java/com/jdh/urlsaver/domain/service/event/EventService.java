package com.jdh.urlsaver.domain.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(final ApplicationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}