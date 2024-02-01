package com.oomool.api.domain.room.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public String dateFormat = "yyyy-MM-dd";
    public String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    public Date parseDate(String dateString) throws ParseException {
        return new SimpleDateFormat(dateFormat).parse(dateString);
    }

    public LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public String convertDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        return dateTime.format(formatter);
    }
}
