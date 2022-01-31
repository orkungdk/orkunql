package com.roche.dia.dashboardbe.constants;

import lombok.Getter;

/**
 * @author orkun.gedik
 */
@Getter
public enum SQLOperator {

    EQUALS(" = "),
    LIKE(" LIKE "),
    ILIKE(" ILIKE "),
    IN(" IN "),
    NOT_IN(" NOT IN "),
    GREATER_OR_EQUAL(" >= "),
    LESS_OR_EQUAL(" <= "),
    IS_NULL(" IS NULL"),
    IS_NOT_NULL(" IS NOT NULL");

    private String value;

    SQLOperator(String value) {
        this.value = value;
    }

}
