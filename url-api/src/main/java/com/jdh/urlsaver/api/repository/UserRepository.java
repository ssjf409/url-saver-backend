package com.jdh.urlsaver.api.repository;

import com.jdh.urlsaver.domain.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLoginId(String loginId);

    Optional<UserEntity> findByUserId(Long userId);
}
