package com.ritesh.edufleet.config;

import com.ritesh.edufleet.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    private final UserServiceImpl userService;

    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint, RestAccessDeniedHandler restAccessDeniedHandler, UserServiceImpl userService) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/signup", "/auth/login").permitAll()
                                .anyRequest()
                                .authenticated()

                )
                .userDetailsService(userService)
                .exceptionHandling(ex -> ex.accessDeniedHandler(restAccessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .build();
    }
}
