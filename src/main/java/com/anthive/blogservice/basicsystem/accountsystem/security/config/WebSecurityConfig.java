package com.anthive.blogservice.basicsystem.accountsystem.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth-> auth
                    .requestMatchers("/account/register","/css/**","/js/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
            .formLogin(form->form
                    .loginPage("/account/login")
                    .usernameParameter("loginId")
                    .passwordParameter("password")
                    .permitAll());

        return http.build();
    }

}
