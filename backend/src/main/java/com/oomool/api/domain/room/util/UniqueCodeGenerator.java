package com.oomool.api.domain.room.util;

import java.security.SecureRandom;
import java.util.Arrays;

public class UniqueCodeGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 8;
    private final SecureRandom random = new SecureRandom();

    public String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int idx = 0; idx < LENGTH; idx++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
