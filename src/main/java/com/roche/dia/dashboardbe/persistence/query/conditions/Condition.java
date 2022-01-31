package com.roche.dia.dashboardbe.persistence.query.conditions;

import com.roche.dia.dashboardbe.constants.SQLOperator;
import com.roche.dia.dashboardbe.utils.AssertUtils;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.roche.dia.dashboardbe.constants.QueryConstants.AND;
import static com.roche.dia.dashboardbe.constants.QueryConstants.NULL_CHECK_FORMAT;


/**
 * @author orkun.gedik
 */
public class Condition<I> implements ICondition {

    private SQLOperator operator;

    private String field;

    private I[] value;

    public Condition(SQLOperator operator, String field, I... value) {
        AssertUtils.assertNotNull(field, value, operator);

        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public Condition(SQLOperator operator, String field, String value) {
        AssertUtils.assertNotNull(field, value, operator);

        this.field = field;
        this.value = (I[]) List.of(value).toArray();
        this.operator = operator;
    }

    public Condition(SQLOperator operator, String alias, String field, I... value) {
        AssertUtils.assertNotNull(alias, field, value, operator);

        this.field = alias.concat(".").concat(field);
        this.value = value;
        this.operator = operator;
    }

    @Override
    public String invoke() {
        StringBuffer query = initializeBuffer();

        if (isMultipleInputOperator()) {
            List<String> inConditions = new ArrayList<>();
            for (I val : value) {
                if (isComplexPrimitive(val)) {
                    inConditions.add(val.toString());
                } else {
                    inConditions.add(String.format("'%s'", val.toString()));
                }
            }
            query.append(String.format("(%s)", String.join(",", inConditions)));
        } else {
            if (isTextCheck()) {
                query.append("'%").append(String.format("%s", value[0])).append("%'");
            } else if (isComplexPrimitive(value[0])) {
                query.append(value[0]);
            } else if (isDateField(value[0])) {
                query.append(String.format("TO_DATE('%s', 'YYYY-MM-DD HH24:MI:SS.FF0')", value[0].toString()));
            } else {
                query.append(String.format("'%s'", value[0]));
            }
        }
        return String.format("(%s)", query.toString());
    }

    private StringBuffer initializeBuffer() {
        return new StringBuffer().append(String.format(NULL_CHECK_FORMAT, field))
                                 .append(AND)
                                 .append(String.format("%s %s ", field, operator.getValue()));
    }

    private boolean isMultipleInputOperator() {
        return operator.equals(SQLOperator.IN) || operator.equals(SQLOperator.NOT_IN);
    }

    private boolean isTextCheck() {
        return (operator.equals(SQLOperator.LIKE) || operator.equals(SQLOperator.ILIKE)) && value[0] instanceof String;
    }

    private boolean isComplexPrimitive(I val) {
        return val instanceof Number || val instanceof Boolean;
    }

    private boolean isDateField(I val) {
        return val instanceof Timestamp || val instanceof DateTime;
    }
}
