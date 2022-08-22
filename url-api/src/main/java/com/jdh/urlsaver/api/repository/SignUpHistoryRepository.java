package com.jdh.urlsaver.api.repository;

import com.jdh.urlsaver.domain.model.entity.auth.SignUpHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignUpHistoryRepository extends JpaRepository<SignUpHistoryEntity, Long> {

    SignUpHistoryEntity findByKeyValue(String keyValue);
}
