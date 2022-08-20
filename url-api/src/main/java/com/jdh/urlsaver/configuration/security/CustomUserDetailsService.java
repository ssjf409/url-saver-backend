package com.jdh.urlsaver.configuration.security;

import com.jdh.urlsaver.api.repository.UserRepository;
import com.jdh.urlsaver.model.entity.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // [Spring security] 4 - login
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLoginId(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(
                    String.format("failed to find User with loginId, loginId: %s", username));
        }
        return UserPrincipal.create(userEntity);
    }
}
