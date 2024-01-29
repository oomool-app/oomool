package com.oomool.api.domain.user.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.oomool.api.domain.user.auth.user.OAuth2UserInfo;
import com.oomool.api.domain.user.entity.User;

public class OAuth2UserPrincipal implements OAuth2User, UserDetails {

    private final OAuth2UserInfo userInfo;
    private final User user = new User();

    public OAuth2UserPrincipal(OAuth2UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userInfo.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "ROLE_" + user.getRole();
        }); //add에 들어올 파라미터는 GrantedAuthority밖에 없으니

        return collectors;
    }

    @Override
    public String getName() {
        return userInfo.getEmail();
    }

    public OAuth2UserInfo getUserInfo() {
        return userInfo;
    }
}
