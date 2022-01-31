package com.roche.dia.dashboardbe.persistence.query.group;

import com.roche.dia.dashboardbe.persistence.query.QueryElement;
import lombok.AllArgsConstructor;

/**
 * @author orkun.gedik
 */
@AllArgsConstructor
public class Group implements QueryElement {

    private String[] fields;

    @Override
    public String invoke() {
        return String.format(" GROUP BY %s ", String.join(",", fields));
    }
}
