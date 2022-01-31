package com.roche.dia.dashboardbe.persistence.query.conditions;

import com.roche.dia.dashboardbe.constants.SQLOperator;
import com.roche.dia.dashboardbe.utils.AssertUtils;

/**
 * @author orkun.gedik
 */
public class NullOperatorCondition implements ICondition {

    private String field;

    private SQLOperator operator;

    public NullOperatorCondition(SQLOperator operator, String field) {
        AssertUtils.assertNotNull(field, operator);

        this.field = field;
        this.operator = operator;
    }

    @Override
    public String invoke() {
        return String.format("(%s %s)", field, operator.getValue());
    }
}
