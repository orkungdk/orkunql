package com.roche.dia.dashboardbe.utils;

import com.roche.dia.dashboardbe.exception.ErrorException;
import com.roche.dia.dashboardbe.exception.ErrorType;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

/**
 * @author orkun.gedik
 */
@UtilityClass
public class AssertUtils {

    public static void assertNotNull(ErrorType errorType, Object... args) {
        Arrays.stream(args).forEach(arg -> {
            if (ObjectUtils.isEmpty(arg)) throw new ErrorException(errorType);
        });
    }

    public static void assertNotNull(Object... args) {
        if (ObjectUtils.isEmpty(args)) {
            throw new ErrorException(ErrorType.INTERNAL_SERVER_ERROR, "List null or empty!");
        }
        assertNotNull(ErrorType.INTERNAL_SERVER_ERROR, args);
    }

    public static void assertTrue(boolean bool, ErrorType errorType, String details) {
        if (!bool) {
            throw new ErrorException(errorType);
        }
    }

    public static void assertPositive(int value, ErrorType errorType, String details) {
        if (value < 0) {
            throw new ErrorException(errorType, details);
        }
    }

    public static <I extends Object> void assertEqualsAny(I actual, ErrorType errorType, String details, I... expected) {
        assertNotNull(expected);

        for (Object val : expected) {
            if (actual.equals(val)) {
               return;
            }
        }
        throw new ErrorException(errorType, details);
    }
}
