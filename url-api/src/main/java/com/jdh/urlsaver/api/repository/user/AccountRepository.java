package com.jdh.urlsaver.api.repository.user;

import com.jdh.urlsaver.model.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountId(String accountId);

    Account findByLoginId(String loginId);
}
