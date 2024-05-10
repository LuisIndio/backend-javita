package com.example.api.web1.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {


        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final AuthenticationProvider authProvider;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
        {
            return http
                    .csrf(csrf ->
                            csrf
                            .disable())
                    .authorizeHttpRequests(authorizeHttpRequests ->
                            authorizeHttpRequests
                                    .requestMatchers("/auth/**").permitAll()
                                    .anyRequest().permitAll()
                    ).sessionManagement(sessionManagement ->
                            sessionManagement
                                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    ).authenticationProvider(authProvider)
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                    .build();

        }


}
