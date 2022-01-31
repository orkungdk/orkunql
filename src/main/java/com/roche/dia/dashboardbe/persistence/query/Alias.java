package com.roche.dia.dashboardbe.persistence.query;

import lombok.experimental.UtilityClass;

/**
 * @author orkun.gedik
 */
@UtilityClass
public class Alias {

    public static String of(String alias, String field) {
        return String.format("%s.%s", alias, field);
    }
}
