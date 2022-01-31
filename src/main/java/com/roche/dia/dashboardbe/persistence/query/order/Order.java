package com.roche.dia.dashboardbe.persistence.query.order;

import com.roche.dia.dashboardbe.persistence.query.QueryElement;
import com.roche.dia.dashboardbe.utils.AssertUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.roche.dia.dashboardbe.constants.QueryConstants.ORDER_BY;

/**
 * @author orkun.gedik
 */
public class Order implements QueryElement {

    private LinkedHashMap<String,Sort.Direction> orderMap;

    private boolean nullable;

    public Order(Sort.Direction direction, String field) {
        AssertUtils.assertNotNull(direction, field);

        this.orderMap = new LinkedHashMap();
        this.orderMap.put(field, direction);
    }

    public Order(Sort.Direction direction, String field, boolean nullable) {
        AssertUtils.assertNotNull(direction, field);

        this.orderMap = new LinkedHashMap();
        this.orderMap.put(field, direction);
        this.nullable = nullable;
    }

    public Order(LinkedHashMap<String,Sort.Direction> orderMap){
        this.orderMap = orderMap;
    }

    public Order(LinkedHashMap<String,Sort.Direction> orderMap, boolean nullable){
        this.orderMap = orderMap;
        this.nullable = nullable;

    }

    @Override
    public String invoke() {
        List<String> subQueries = new ArrayList<>();

        for (Map.Entry<String,Sort.Direction> entry : orderMap.entrySet()) {
            String field = entry.getKey();
            Sort.Direction direction = entry.getValue();
            subQueries.add(generateFormat(field, direction));
        }

        return ORDER_BY + String.join("," ,subQueries);
    }

    private String generateFormat(String field, Sort.Direction direction) {
        return nullable ? String.format("%s %s", field, direction.name())
                        : String.format(" %s IS NULL ASC, %s %s", field, field, direction.name());
    }
}
