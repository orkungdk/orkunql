package com.roche.dia.dashboardbe.persistence.query.builders;

import com.roche.dia.dashboardbe.constants.QueryConstants;
import com.roche.dia.dashboardbe.persistence.query.conditions.ICondition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author orkun.gedik
 */
public interface IQueryOperationBuilder {

    String build();


    default String formatCondition(List<ICondition> conditions) {
        String formattedConditions = String.join(QueryConstants.AND, conditions.stream()
                .map(ICondition::invoke)
                .collect(Collectors.toList()));
        return formattedConditions.isEmpty() ? formattedConditions : QueryConstants.AND + formattedConditions;
    }

}
