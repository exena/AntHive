package com.anthive.blogservice.accountsystem.base;

import com.anthive.blogservice.accountsystem.base.dto.AccountRegisterRequest;
import com.anthive.blogservice.accountsystem.base.model.Account;
import com.anthive.blogservice.accountsystem.base.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(AccountRegisterRequest request){
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Role role = new Role(1L);
        Account account = new Account(request.getLoginId(),encodedPassword,true, List.of(role));
        accountRepository.save(account);
    }

    public boolean isDuplicatedId(String loginId){
        return accountRepository.findByLoginId(loginId) != null;
    }
}