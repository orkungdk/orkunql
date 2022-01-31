package com.roche.dia.dashboardbe.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author orkun.gedik
 */
@Getter
@Setter
public class ResourceAudit {

    private String by;

    private Timestamp at;

}
