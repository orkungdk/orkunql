package com.roche.dia.dashboardbe.persistence.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;


/**
 * @author orkun.gedik
 */
@Getter
@Setter
@ToString
public class AuditableEntity extends AbstractEntity {

    /**
     * The unique identifier column of database tables
     */
    private Long id;

    private String createdBy;

    private Timestamp createdAt;

    private String updatedBy;

    private Timestamp updatedAt;

}
