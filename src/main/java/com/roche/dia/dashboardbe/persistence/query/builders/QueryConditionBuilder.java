package com.roche.dia.dashboardbe.persistence.query.builders;

import com.roche.dia.dashboardbe.constants.SQLOperator;
import com.roche.dia.dashboardbe.exception.ErrorType;
import com.roche.dia.dashboardbe.persistence.query.conditions.Condition;
import com.roche.dia.dashboardbe.persistence.query.conditions.ICondition;
import com.roche.dia.dashboardbe.persistence.query.conditions.NullOperatorCondition;
import com.roche.dia.dashboardbe.utils.AssertUtils;
import com.roche.dia.dashboardbe.utils.ListUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author orkun.gedik
 */
public class QueryConditionBuilder {

    private static final Logger logger = LogManager.getLogger(QueryConditionBuilder.class);

    protected static <I> void addCondition(SQLOperator operator, String field, List<I> values, List<ICondition> conditions) {
        if (ListUtils.notEmpty(values)) {
            conditions.add(new Condition<>(operator, field, values.toArray()));
            logger.debug("{} condition is added for values {}.", field, values);
        }
    }

    protected static <I> void addCondition(SQLOperator operator, String field, I value, List<ICondition> conditions) {
        if (!ObjectUtils.isEmpty(value)) {
            conditions.add(new Condition<I>(operator, field, value));
            logger.debug("{} condition is added for values {}.", field, value);
        }
    }

    protected static void addCondition(List<ICondition> conditions, List<ICondition> finalList) {
        if (ListUtils.notEmpty(finalList) && ListUtils.notEmpty(conditions)) {
            finalList.addAll(conditions);
        }
    }

    protected static void addCondition(ICondition condition, List<ICondition> finalList) {
        if (Objects.nonNull(condition)) {
            finalList.add(condition);
        }
    }

    protected static void addCondition(SQLOperator operator, String field, List<ICondition> conditions) {
        AssertUtils.assertEqualsAny(operator, ErrorType.INTERNAL_SERVER_ERROR, "Invalid Operator.", SQLOperator.IS_NULL, SQLOperator.IS_NOT_NULL);
        conditions.add(new NullOperatorCondition(operator, field));
    }
}
