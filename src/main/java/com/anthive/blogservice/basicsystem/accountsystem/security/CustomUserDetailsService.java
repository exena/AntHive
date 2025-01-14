package com.anthive.blogservice.basicsystem.accountsystem.security;

import com.anthive.blogservice.basicsystem.accountsystem.base.AccountRepository;
import com.anthive.blogservice.basicsystem.accountsystem.base.model.Account;
import com.anthive.blogservice.basicsystem.accountsystem.security.dto.AccountContext;
import com.anthive.blogservice.basicsystem.accountsystem.security.dto.CustomPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByLoginId(username);

        if(account == null){
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }
        List<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        CustomPrincipal customPrincipal = new CustomPrincipal(account.getId(), account.getLoginId(), account.getPassword(), account.getEnabled());
        return new AccountContext(customPrincipal, authorities);
    }

}