package com.roche.dia.dashboardbe.utils;

import com.google.common.base.Strings;
import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author orkungedik
 */
@UtilityClass
public final class TimeUtils {

    public static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static long HOURS_IN_MS = 1000 * 60 * 60;
    public static long MINS_IN_MS = 1000 * 60;

    public static Timestamp now() {
        return Timestamp.from(Instant.now());
    }

    public static Timestamp convertToTimestamp(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.from(DATE_TIME_FORMATTER.parse(date));

        return Timestamp.valueOf(localDateTime);
    }

}
