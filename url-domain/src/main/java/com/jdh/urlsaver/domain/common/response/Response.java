//package com.jdh.urlsaver.domain.common.response;
//
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Builder
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class Response<T> {
//
//    private T data;
//    private Error error;
//
//    public static <T> Response<T> ok(T data) {
//        return new Response<>(data, null);
//    }
//
//    public static <T> Response<T> error(String code) {
//        return error(code, null);
//    }
//
//    public static <T> Response<T> error(String code, T data) {
//        return new Response<>(data, new Error(code));
//    }
//
////    @Data
////    @NoArgsConstructor
////    public static class Error {
////
////        private String code;
////        private String message;
////
////        public Error(String code) {
////            this.code = code;
//////            this.message = MsgTranslator.getErrorMessage(code);
//////            this.message = MessageSourceTranslator.getStaticMessage()
////        }
////
////        public Error(String code, String message) {
////            this.code = code;
////            this.message = message;
//////            this.message = MsgTranslator.getErrorMessage(code) + " (" + message + ")";
////        }
////
////        @Override
////        public String toString() {
////            return "Error{" +
////                   "code='" + code + '\'' +
////                   ", description='" + message + '\'' +
////                   '}';
////        }
////    }
//}
