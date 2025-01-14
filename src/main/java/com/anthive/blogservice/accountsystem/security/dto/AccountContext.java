package com.anthive.blogservice.accountsystem.security.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class AccountContext implements UserDetails {

    private final CustomPrincipal customPrincipal;

    private final List<GrantedAuthority> authorities;

    public Long getId() {
        return customPrincipal.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return customPrincipal.getPassword();
    }

    @Override
    public String getUsername() {
        return customPrincipal.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return customPrincipal.getEnabled();
    }
}