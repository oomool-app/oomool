package com.oomool.api.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 현재 시간을 반환하는 메서드
 */
public class CurrentTime {

    public static String getCurrentTime() {
        // 현재 시간을 가져옵니다.
        LocalDateTime currentTime = LocalDateTime.now();

        // 날짜와 시간을 원하는 형식으로 포맷합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        return formattedTime;
    }
}
