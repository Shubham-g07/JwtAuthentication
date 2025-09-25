package com.jwt.config;


import com.jwt.Services.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class securityConfig {

    @Autowired
    private jwtAuthEntry entryPoint;

    @Autowired
    private jwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    /**
     * Configure authentication provider
     */

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    /**
     * Expose AuthenticationManager bean
     */

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    /**
     * Password encoder – NoOp for now (not recommended in prod)
     */

    @Bean
    protected PasswordEncoder passwordEncoder(){
        /*return new BCryptPasswordEncoder();*/

        return NoOpPasswordEncoder.getInstance();
    }


    /**
     * Security filter chain config
     */

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.
                authorizeHttpRequests( auth -> auth
                        .requestMatchers("/token").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                // to off the session storage
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}