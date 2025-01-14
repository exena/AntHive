package com.anthive.blogservice.accountsystem.base;

import com.anthive.blogservice.accountsystem.base.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByLoginId(String loginId);
}
