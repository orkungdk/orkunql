package com.roche.dia.dashboardbe.persistence.query.conditions;

import java.util.List;

import static com.roche.dia.dashboardbe.constants.QueryConstants.OR;

/**
 * @author orkun.gedik
 */
public class SeparatedCondition implements ICondition {

    private List<ICondition> conditions;

    public SeparatedCondition(List<ICondition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String invoke() {
        StringBuffer query = new StringBuffer();

        conditions.stream().forEach(condition -> {
            if (!query.toString().isEmpty()) {
                query.append(OR);
            }
            query.append(condition.invoke());
        });

        return String.format("(%s)", query.toString());
    }
}
