package com.oomool.api.domain.room.util;

import java.security.SecureRandom;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UniqueCodeGenerator {

    public static String generateRandomString(int length) {
        final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int idx = 0; idx < length; idx++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

}
