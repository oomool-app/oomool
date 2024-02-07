package com.oomool.api.domain.auth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * OAuth2 인증 시 공급자 예외 처리
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
