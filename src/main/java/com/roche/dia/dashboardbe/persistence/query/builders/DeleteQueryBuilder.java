package com.roche.dia.dashboardbe.persistence.query.builders;

import com.roche.dia.dashboardbe.constants.QueryConstants;
import com.roche.dia.dashboardbe.persistence.query.conditions.ICondition;
import com.roche.dia.dashboardbe.utils.AssertUtils;
import com.roche.dia.dashboardbe.utils.ListUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author orkun.gedik
 */
public class DeleteQueryBuilder implements IQueryOperationBuilder {

    private String tableName;

    private List<ICondition> conditions;

    public String build() {
        AssertUtils.assertNotNull(tableName);

        StringBuilder stringBuilder = new StringBuilder()
                .append(QueryConstants.DELETE)
                .append(QueryConstants.FROM)
                .append(tableName);
        if (ListUtils.notEmpty(conditions)) {
            stringBuilder.append(" WHERE 1=1 ").append(formatCondition(conditions));
        }
        return stringBuilder.toString();
    }

    public DeleteQueryBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public DeleteQueryBuilder conditions(List<ICondition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public DeleteQueryBuilder condition(ICondition... conditions) {
        this.conditions = Arrays.asList(conditions);
        return this;
    }
}
