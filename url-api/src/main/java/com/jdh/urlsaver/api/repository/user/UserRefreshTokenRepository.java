package com.jdh.urlsaver.api.repository.user;

import com.jdh.urlsaver.api.entity.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<Token, Long> {
    Token findByUserId(String userId);

    Token findByUserIdAndRefreshToken(String userId, String refreshToken);
}
