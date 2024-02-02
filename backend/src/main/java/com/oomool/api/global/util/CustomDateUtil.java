package com.oomool.api.global.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CustomDateUtil {

    // date Format
    public String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    // String to LocalDateTime
    public LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    // LocalDateTime to String
    public String convertDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return dateTime.format(formatter);
    }

    /**
     * DateInteval 계산
     * */
    public int getDateInterval(LocalDate startDate, LocalDate endDate) {
        return (int)ChronoUnit.DAYS.between(startDate, endDate);
    }

}
