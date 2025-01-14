package com.anthive.blogservice.postsystem;

import com.anthive.blogservice.accountsystem.base.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByAccount(Account account, Pageable pageable);
}
