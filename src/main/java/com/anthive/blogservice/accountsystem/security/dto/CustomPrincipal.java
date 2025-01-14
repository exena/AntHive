package com.anthive.blogservice.accountsystem.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomPrincipal {
    private Long id;
    private String loginId;
    private String password;
    private Boolean enabled;
}
