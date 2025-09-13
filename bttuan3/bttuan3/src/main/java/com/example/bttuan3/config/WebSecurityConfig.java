    package com.example.bttuan3.config;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.ProviderManager;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.core.userdetails.UserDetailsService;

    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfig {

        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(userDetailsService);
            provider.setPasswordEncoder(passwordEncoder);
            return new ProviderManager(provider);
        }

        @Bean
        protected PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    @Bean
        protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                .requestMatchers("/auth/register", "/auth/process-register").permitAll()
                .requestMatchers("/auth/login", "/auth/logout").permitAll()
                .requestMatchers("/users").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/users/add", 
                                "/users/update/**", 
                                "/users/delete/**", 
                                "/users/edit/**", 
                                "/users/assign-role").hasAuthority("ADMIN")
                .anyRequest().permitAll()

            )
            .formLogin(form -> form
                .loginPage("/auth/login")        
                .loginProcessingUrl("/login")      
                .usernameParameter("email")        
                .defaultSuccessUrl("/users", true) 
                .permitAll()
            )
            .logout(config -> config.logoutUrl("/auth/logout")
                                    .logoutSuccessUrl("/auth/login?logout=true"));
        return http.build();
    }
    }

