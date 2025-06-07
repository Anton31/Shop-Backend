package com.example.authserverresourceserversameapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final OidcUserInfoService oidcUserInfoService;
    private final UserDetailsService userDetailsService;

    public MyConverter(OidcUserInfoService oidcUserInfoService, UserDetailsService userDetailsService) {
        this.oidcUserInfoService = oidcUserInfoService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getSubject());
        OidcUserInfo oidcUserInfo = oidcUserInfoService.loadUser("user1");
        String name = oidcUserInfo.getClaim("given_name");
        System.out.println(name);
        List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
        List<GrantedAuthority> newAuthorities = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            newAuthorities.add(new SimpleGrantedAuthority("ROLE_" + authority.getAuthority()));
        }
        System.out.println(newAuthorities);
        return new JwtAuthenticationToken(jwt, newAuthorities);
    }
}