package com.anthive.blogservice.basicsystem.accountsystem.base;

import com.anthive.blogservice.basicsystem.accountsystem.base.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
