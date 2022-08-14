package com.jdh.urlsaver.api.repository;

import com.jdh.urlsaver.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLoginId(String loginId);
}
