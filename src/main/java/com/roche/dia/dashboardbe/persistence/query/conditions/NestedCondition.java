package com.roche.dia.dashboardbe.persistence.query.conditions;

import com.roche.dia.dashboardbe.utils.AssertUtils;

import java.util.List;

import static com.roche.dia.dashboardbe.constants.QueryConstants.AND;

/**
 * @author orkun.gedik
 */
public class NestedCondition implements ICondition {

    private List<ICondition> coupledConditions;

    public NestedCondition(List<ICondition> coupledConditions) {
        AssertUtils.assertNotNull(coupledConditions);

        this.coupledConditions = coupledConditions;
    }

    @Override
    public String invoke() {
        StringBuffer query = new StringBuffer();

        coupledConditions.stream().forEach(condition -> {
            if (!query.toString().isEmpty()) {
                query.append(AND);
            }
            query.append(condition.invoke());
        });

        return String.format("(%s)", query.toString());
    }
}
