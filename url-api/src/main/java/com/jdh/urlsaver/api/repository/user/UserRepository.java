package com.jdh.urlsaver.api.repository.user;

import com.jdh.urlsaver.model.entity.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    User findByLoginId(String loginId);

    User findByLoginId(String loginId);
}
