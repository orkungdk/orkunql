package com.roche.dia.dashboardbe.persistence.query.builders;

import com.roche.dia.dashboardbe.constants.QueryConstants;
import com.roche.dia.dashboardbe.utils.AssertUtils;

/**
 * @author orkun.gedik
 */
public class InsertQueryBuilder implements IQueryOperationBuilder {

    private String insertQuery;

    private String tableName;

    private Object[] values;

    public String build() {
        AssertUtils.assertNotNull(insertQuery, tableName);

        return String.format(QueryConstants.INSERT + tableName + insertQuery, values);
    }

    public InsertQueryBuilder insertQuery(String insertQuery) {
        this.insertQuery = insertQuery;
        return this;
    }

    public InsertQueryBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public InsertQueryBuilder values(Object... values) {
        this.values = values;
        return this;
    }

}
