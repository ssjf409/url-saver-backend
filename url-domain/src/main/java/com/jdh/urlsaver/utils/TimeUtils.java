package com.jdh.urlsaver.utils;

import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class TimeUtils {
    public static LocalDate toLocalDate(@Nullable Date date) {
        if (date == null) {
            return LocalDate.now();
        }
        return LocalDate.ofInstant(date.toInstant(), ZoneOffset.UTC);
    }

    public static LocalDateTime toLocalDateTime(@Nullable Date date) {
        if (date == null) {
            return LocalDateTime.now();
        }

        return LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
    }
}
