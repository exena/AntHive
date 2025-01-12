package com.anthive.blogservice.basicsystem.accountsystem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
