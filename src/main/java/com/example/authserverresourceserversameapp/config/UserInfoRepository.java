package com.example.authserverresourceserversameapp.config;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserInfoRepository {
    private final Map<String, Map<String, Object>> userInfo = new HashMap<>();

    public UserInfoRepository() {
        this.userInfo.put("user1", createUser("Anton", "Momot"));
        this.userInfo.put("user2", createUser("Igor", "Kuzmanenko"));
    }

    public Map<String, Object> findByUsername(String username) {
        return this.userInfo.get(username);
    }

    public Map<String, Object> createUser(String firstName, String lastName) {
        return OidcUserInfo.builder()
                .subject(firstName)
                .name(firstName + " " + lastName)
                .givenName(firstName)
                .familyName(lastName)
                .preferredUsername(firstName)
                .build()
                .getClaims();
    }
}

