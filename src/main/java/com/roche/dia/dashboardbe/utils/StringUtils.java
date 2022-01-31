package com.roche.dia.dashboardbe.utils;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

/**
 * @author orkun.gedik
 */
@UtilityClass
public class StringUtils {

    /**
     * This method compares two string and checks whether they are equals or not.
     * Also this method allows to return value in case of second argument is null and it is an expected business behaviour.
     *
     * @param str1          nullable {@link String}
     * @param str2          nullable {@link String}
     * @param nullCondition to be returned value if {@code str2} is {@code null}
     * @return
     */
    public static boolean equals(String str1, @Nullable String str2, boolean nullCondition) {
        if (str1 == null) {
            return str2 == null;
        } else {
            return str2 == null ? nullCondition : str1.equals(str2);
        }
    }

    /**
     * This method compares two string and checks whether they are equals or not.
     * Also this method allows to return value in case of second argument is null and it is an expected business behaviour.
     *
     * @param str1          nullable {@link String}
     * @param str2          nullable {@link String}
     * @param nullCondition to be returned value if {@code str2} is {@code null}
     * @return
     */
    public static boolean notEquals(String str1, @Nullable String str2, boolean nullCondition) {
        return !equals(str1, str2, nullCondition);
    }
}
