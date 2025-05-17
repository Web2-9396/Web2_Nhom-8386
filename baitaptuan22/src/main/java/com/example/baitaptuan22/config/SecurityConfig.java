// package com.example.baitaptuan22.config;

// import org.springframework.context.annotation.Bean;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// public class SecurityConfig {
// // Configuration for security settings
// // This class can be used to define security filters, authentication
// providers, etc.

// // Example:
// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// .authorizeRequests()
// .antMatchers("/public/**").permitAll()
// .anyRequest().authenticated()
// .and()
// .formLogin();
// return http.build();
// }
// }
