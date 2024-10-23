package com.example.authserverresourceserversameapp.config;

import com.example.authserverresourceserversameapp.service.AppUserDetailsService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final AppUserDetailsService userDetailsService;

    public MyConverter(AppUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getSubject());
        List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
        List<GrantedAuthority> newAuthorities = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            newAuthorities.add(new SimpleGrantedAuthority("ROLE_" + authority.getAuthority()));
        }
        return new JwtAuthenticationToken(jwt, newAuthorities);
    }
}