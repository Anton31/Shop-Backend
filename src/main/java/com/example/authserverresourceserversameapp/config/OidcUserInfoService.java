package com.example.authserverresourceserversameapp.config;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

@Service
public class OidcUserInfoService {
    private final UserInfoRepository userInfoRepository;

    public OidcUserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public OidcUserInfo loadUser(String username) {
        return new OidcUserInfo(this.userInfoRepository.findByUsername(username));
    }
}
