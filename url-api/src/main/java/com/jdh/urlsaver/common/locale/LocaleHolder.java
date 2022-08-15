package com.jdh.urlsaver.common.locale;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class LocaleHolder {

    public static final Locale DEFAULT_LOCALE = Locale.KOREA;

    public static Locale getLocale() {
        if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            LocaleContextHolder.setLocale(Locale.US, Boolean.TRUE);
        } else if (LocaleContextHolder.getLocale().getLanguage().equals(Locale.KOREAN.getLanguage())) {
            LocaleContextHolder.setLocale(Locale.KOREA, Boolean.TRUE);
        } else {
            LocaleContextHolder.setLocale(LocaleContextHolder.getLocale(), Boolean.TRUE);
        }
        return LocaleContextHolder.getLocale();
    }

    public static String getStringLocaleString() {
        return getLocale().toString().replace("_", "-");
    }
}
