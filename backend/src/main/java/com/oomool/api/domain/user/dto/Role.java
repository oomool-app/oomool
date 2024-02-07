package com.oomool.api.domain.user.dto;

import lombok.Getter;

/**
 * 로그인 안한 상태 : 어나니머스
 * 로그인 한 상태 : ROLE_USER
 */
@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    String value;

    Role(String value) {
        this.value = value;
    }
}
