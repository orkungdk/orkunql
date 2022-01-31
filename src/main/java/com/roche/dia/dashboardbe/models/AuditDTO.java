package com.roche.dia.dashboardbe.models;

import lombok.Getter;
import lombok.Setter;

/**
 * @author orkun.gedik
 */
@Getter
@Setter
public abstract class AuditDTO implements DataTransferObject {
    
    private Long id;

    private MetaData meta;

}
