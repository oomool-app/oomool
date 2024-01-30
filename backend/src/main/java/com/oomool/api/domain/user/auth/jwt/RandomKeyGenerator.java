package com.oomool.api.domain.user.auth.jwt;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * 시큐어 key 발급하는 클래스
 * JWT secret key 크기 최소 32바이트(256비트)
 */
public class RandomKeyGenerator {

    public static void main(String[] args) {
        // 시큐어한 랜덤 키 생성
        byte[] key = generateRandomKey(32); // 32바이트(256비트) 키

        // Base64 인코딩
        String base64Key = Base64.getUrlEncoder().withoutPadding().encodeToString(key);

        System.out.println("Generated Key: " + base64Key);
    }

    private static byte[] generateRandomKey(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[length];
        secureRandom.nextBytes(key);
        return key;
    }
}
