package com.example.authserverresourceserversameapp.config;

import com.example.authserverresourceserversameapp.service.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.UUID;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {
    private final AppUserDetailsService userDetailsService;
    private final MyConverter converter;

    public DefaultSecurityConfig(AppUserDetailsService userDetailsService,
                                 MyConverter converter) {
        this.userDetailsService = userDetailsService;
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        System.out.println("default");
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"),
                                        AntPathRequestMatcher.antMatcher("/user/**"),
                                        AntPathRequestMatcher.antMatcher("/images/**"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/products/**"))
                                .permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/products/**"))
                                .hasRole("admin")
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/products/**"))
                                .hasRole("admin")
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/products/**"))
                                .hasRole("admin")
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/cart/**"))
                                .hasAnyRole("user", "admin"))
                .formLogin(withDefaults());
        http.cors(withDefaults());
        http.oauth2ResourceServer((resourceServer) -> resourceServer.jwt(jwtConfigurer -> jwtConfigurer
                .jwtAuthenticationConverter(converter)));
        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        System.out.println("client");
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("app-client")
                .clientSecret("$2a$12$MyTjNEL1JGagTDhPTHYaOuFNTkpegx.WFXuZDRlDkH51R.QGfP3Be")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope(OidcScopes.OPENID)
                .redirectUri("http://localhost:4200")
                .build();
        return new InMemoryRegisteredClientRepository(client);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
