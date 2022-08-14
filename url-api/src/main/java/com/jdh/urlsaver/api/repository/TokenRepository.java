package com.jdh.urlsaver.api.repository;

import com.jdh.urlsaver.model.entity.auth.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

//    TokenEntity findByAccessToken(String accessToken);
//
//    TokenEntity findByDeviceId(String deviceId);

    TokenEntity findByTypeAndHeaderAndHeaderValue(String type, String header, String headerValue);
}
