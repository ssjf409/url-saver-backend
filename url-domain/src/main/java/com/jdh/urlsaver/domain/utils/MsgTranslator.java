//package com.jdh.urlsaver.utils;
//
//public class MsgTranslator {
//
//    static Config msgConfig = ConfigLoader.load("msgMap", "msgMap");
//    static Config errorMsgConfig = msgConfig.getConfig("errorMap");
//
//    private MsgTranslator() {
//        throw new IllegalStateException("Utility class");
//    }
//
//    public static String getErrorMessage(String errorCode) {
//        return getEnErrorMessage(errorCode);
//    }
//
//    public static String getEnErrorMessage(String errorCode) {
//        return errorMsgConfig.getCofig("en").getString(errorCode);
//    }
//}