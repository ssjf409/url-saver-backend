package com.jdh.urlsaver.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Slf4j
@Component
public class MessageHandler {

    public static final String MESSAGE_SOURCE_BASE_NAME = "classpath:messages/messages";
    private static final ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource =
            new ReloadableResourceBundleMessageSource();

    static {
        reloadableResourceBundleMessageSource.setCacheSeconds(-1);
        reloadableResourceBundleMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        reloadableResourceBundleMessageSource.setBasenames(MESSAGE_SOURCE_BASE_NAME);
        reloadableResourceBundleMessageSource.setFallbackToSystemLocale(false);
    }

    public static String getMessage(String result, Object... params) {
        return getMessageStatic(result, params, null);
    }

    public static String getMessageStatic(String result, Object[] params, Locale loc) {
        String message = Strings.EMPTY;
        if (StringUtils.isBlank(result)) {
            return message;
        }
        try {
            Locale locale = loc == null? LocaleContextHolder.getLocale() : loc;
            message = reloadableResourceBundleMessageSource.getMessage(result, params, locale);
        } catch (Exception e) {
            log.error("parse message error!", e);
        }
        return message;
    }

    public static String getMessageStatic(String result) {
        return getMessageStatic(result, null, null);
    }
}
