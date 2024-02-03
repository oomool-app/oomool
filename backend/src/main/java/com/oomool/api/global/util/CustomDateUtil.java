package com.oomool.api.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CustomDateUtil {

    // date Format
    public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    // String to LocalDateTime
    public static LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    // LocalDateTime to String
    public static String convertDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return dateTime.format(formatter);
    }

    /**
     * DateInteval 계산
     * */
    public static int getDateInterval(LocalDate startDate, LocalDate endDate) {
        return (int)ChronoUnit.DAYS.between(startDate, endDate);
    }

}
