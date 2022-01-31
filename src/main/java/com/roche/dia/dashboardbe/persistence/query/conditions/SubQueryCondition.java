package com.roche.dia.dashboardbe.persistence.query.conditions;

import com.roche.dia.dashboardbe.constants.SQLOperator;
import com.roche.dia.dashboardbe.utils.AssertUtils;


/**
 * @author orkun.gedik
 */
public class SubQueryCondition implements ICondition {

    private SQLOperator operator;

    private String field;

    private String subQuery;

    public SubQueryCondition(SQLOperator operator, String field, String subQuery) {
        AssertUtils.assertNotNull(field, subQuery, operator);
        this.field = field;
        this.subQuery = subQuery;
        this.operator = operator;
    }

    @Override
    public String invoke() {
        return String.format("(%s %s (%s))", field, operator.getValue(), subQuery);
    }

}
