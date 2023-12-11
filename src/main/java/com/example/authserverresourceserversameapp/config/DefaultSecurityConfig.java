package com.example.authserverresourceserversameapp.config;


import com.example.authserverresourceserversameapp.service.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {

    private final AppUserDetailsService userDetailsService;

    public DefaultSecurityConfig(AppUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers("/user/**", "/images/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/**")
                                .hasAnyAuthority("SCOPE_read", "SCOPE_write")
                                .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("SCOPE_write")
                                .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("SCOPE_write")
                                .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("SCOPE_write")
                )
                .formLogin(withDefaults());
        http.cors(withDefaults());
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        http.oauth2ResourceServer((resourceServer) -> resourceServer.jwt()).apply(authorizationServerConfigurer);
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}