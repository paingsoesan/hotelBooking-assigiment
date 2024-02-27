package com.example.hotelbookingassignment.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(c -> {
                    c.loginPage("/login")
                            .loginProcessingUrl("/auth/login")
                            .defaultSuccessUrl("/")
                            .failureUrl("/auth/error-login")
                            .permitAll();
                })
                .logout(c -> {
                    c.logoutUrl("/logout")
                            .logoutSuccessUrl("/")
                            .permitAll();
                })
                .csrf(c -> {
                    c.disable();
                })
                .authorizeHttpRequests(c -> {
                    c
                            .requestMatchers(
                                    "/bootstrap/**", "/image/**", "/", "/home",
                                    "/rooms", "/contact", "/reservation/**", "/auth/**"
                            )
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                });

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
