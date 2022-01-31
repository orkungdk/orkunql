package com.roche.dia.dashboardbe.persistence.query.builders;

import com.roche.dia.dashboardbe.constants.QueryConstants;
import com.roche.dia.dashboardbe.persistence.query.conditions.ICondition;
import com.roche.dia.dashboardbe.utils.AssertUtils;

import java.util.List;

/**
 * @author orkun.gedik
 */
public class UpdateQueryBuilder implements IQueryOperationBuilder {

    private List<ICondition> conditions;

    private String values;

    private String tableName;

    public String build() {
        AssertUtils.assertNotNull(values, tableName, conditions);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(QueryConstants.UPDATE);
        stringBuilder.append(tableName);
        stringBuilder.append(QueryConstants.SET);
        stringBuilder.append(values);
        stringBuilder.append(QueryConstants.WHERE);
        stringBuilder.append(formatCondition(conditions));

        return stringBuilder.toString();
    }

    public UpdateQueryBuilder conditions(ICondition... conditions) {
        this.conditions = List.of(conditions);
        return this;
    }

    public UpdateQueryBuilder values(String values) {
        this.values = values;
        return this;
    }

    public UpdateQueryBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

}