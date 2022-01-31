package com.roche.dia.dashboardbe.persistence.query.builders;

import com.roche.dia.dashboardbe.constants.QueryConstants;
import com.roche.dia.dashboardbe.constants.SQLOperator;
import com.roche.dia.dashboardbe.models.Pagination;
import com.roche.dia.dashboardbe.persistence.query.conditions.ICondition;
import com.roche.dia.dashboardbe.persistence.query.group.Group;
import com.roche.dia.dashboardbe.persistence.query.order.Order;
import com.roche.dia.dashboardbe.utils.AssertUtils;
import com.roche.dia.dashboardbe.utils.ListUtils;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author orkun.gedik
 */
public class SelectQueryBuilder implements IQueryOperationBuilder, Cloneable {

    private String selectStatement;

    private Integer limit;

    private List<ICondition> conditions;

    private String exclusions;

    private String tableName;

    private Group group;

    private Order order;

    private Long offset;

    public String build() {
        AssertUtils.assertNotNull(selectStatement, tableName);

        var sql = selectStatement + QueryConstants.FROM + tableName;

        if (!CollectionUtils.isEmpty(conditions)) {
            sql += QueryConstants.WHERE + formatCondition(conditions);
        }
        if (!StringUtils.isEmpty(exclusions)) {
            sql += CollectionUtils.isEmpty(conditions) ? QueryConstants.WHERE + exclusions
                                                       : QueryConstants.AND + exclusions;
        }
        if (Objects.nonNull(group)) {
            sql += group.invoke();
        }
        if (Objects.nonNull(order)) {
            sql += order.invoke();
        }
        if (Objects.nonNull(limit)) {
            sql += QueryConstants.LIMIT + limit;
        }
        if (Objects.nonNull(offset)) {
            sql += QueryConstants.OFFSET + offset;
        }
        return sql;
    }

    public SelectQueryBuilder selectStatement(String selectStatement) {
        this.selectStatement = selectStatement;
        return this;
    }

    public SelectQueryBuilder selectStatement(String selectStatement, String... args) {
        this.selectStatement = String.format(selectStatement, args);
        return this;
    }

    public SelectQueryBuilder pagination(Pagination pagination) {
        this.limit(pagination.getPageSize());
        this.offset(pagination.getOffset());

        return this;
    }

    public SelectQueryBuilder conditions(List<ICondition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public SelectQueryBuilder condition(ICondition... conditions) {
        this.conditions = Arrays.asList(conditions);
        return this;
    }

    public SelectQueryBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SelectQueryBuilder tableName(String tableName, String... args) {
        this.tableName = String.format(tableName, args);
        return this;
    }

    public SelectQueryBuilder limit(Integer limit) {
        if (limit != null) {
            this.limit = limit;
        }
        return this;
    }

    public SelectQueryBuilder offset(long offset) {
        this.offset = offset;
        return this;
    }

    public SelectQueryBuilder order(Sort.Direction direction, String field) {
        if (direction != null && field != null) {
            this.order = new Order(direction, field);
        }
        return this;
    }

    public SelectQueryBuilder order(Sort.Direction direction, String field, boolean nullable) {
        if (direction != null && field != null) {
            this.order = new Order(direction, field, nullable);
        }
        return this;
    }

    public SelectQueryBuilder order(LinkedHashMap<String,Sort.Direction> orderMap) {
        if (!orderMap.isEmpty()) {
            this.order = new Order(orderMap);
        }
        return this;
    }

    public SelectQueryBuilder order(LinkedHashMap<String,Sort.Direction> orderMap, boolean nullable) {
        if (!orderMap.isEmpty()) {
            this.order = new Order(orderMap, nullable);
        }
        return this;
    }

    public SelectQueryBuilder order(Sort sort) {
        if (sort != null) {
            LinkedHashMap<String, Sort.Direction> orderMap = new LinkedHashMap<>();

            for (Sort.Order order : sort) {
                orderMap.put(order.getProperty(), order.getDirection());
            }
            this.order = new Order(orderMap);
        }
        return this;
    }

    public SelectQueryBuilder group(String... fields) {
        if (fields != null && fields.length > 0) {
            this.group = new Group(fields);
        }
        return this;
    }

    public SelectQueryBuilder exclusions(String exclusions) {
        this.exclusions = exclusions;
        return this;
    }

    public <I> SelectQueryBuilder addCondition(SQLOperator operator, String field, I value) {
        if (Objects.isNull(this.conditions)) {
           this.conditions = new ArrayList<>();
        }
        QueryConditionBuilder.addCondition(operator, field, value, this.conditions);
        return this;
    }

    public <I> SelectQueryBuilder addCondition(SQLOperator operator, String field, List<I> value) {
        if (Objects.isNull(this.conditions)) {
            this.conditions = new ArrayList<>();
        }
        QueryConditionBuilder.addCondition(operator, field, value, this.conditions);
        return this;
    }

    public <I> SelectQueryBuilder addNullOperatorCondition(SQLOperator operator, String field) {
        if (Objects.isNull(this.conditions)) {
            this.conditions = new ArrayList<>();
        }
        QueryConditionBuilder.addCondition(operator, field, this.conditions);
        return this;
    }

    @SneakyThrows
    public SelectQueryBuilder clone() {
        return (SelectQueryBuilder) super.clone();
    }
}
