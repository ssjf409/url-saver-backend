//package com.jdh.urlsaver.api.controller.user;
//
//import com.jdh.urlsaver.api.service.UserService;
//import com.jdh.urlsaver.common.ApiResponse;
//import com.jdh.urlsaver.model.entity.account.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    @GetMapping
//    public ResponseEntity<ApiResponse> getUser() {
//        org.springframework.security.core.userdetails.User principal =
//                (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
//                                                                                          .getAuthentication()
//                                                                                          .getPrincipal();
//
//        User user = userService.getUser(principal.getUsername());
//
//        return ResponseEntity.ok(ApiResponse.success("user", user));
//    }
//}
