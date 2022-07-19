package com.jdh.urlsaver.oauth.service;

import com.jdh.urlsaver.api.repository.user.UserRepository;
import com.jdh.urlsaver.model.entity.account.User;
import com.jdh.urlsaver.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // [Spring security] 4 - login
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userRepository.findByLoginId(loginId);
        if (user == null) {
            throw new UsernameNotFoundException("can not find username.");
        }


        return UserPrincipal.create(user);
//        UserPrincipal userPrincipal = UserPrincipal.create(user);
//        SecurityContextHolder.getContext().setAuthentication((Authentication) userPrincipal);
//        return userPrincipal;
    }
}
