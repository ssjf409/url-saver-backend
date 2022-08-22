package com.jdh.urlsaver.api.repository;

import com.jdh.urlsaver.domain.model.entity.auth.SignInHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignInHistoryRepository extends JpaRepository<SignInHistoryEntity, Long> {
}
