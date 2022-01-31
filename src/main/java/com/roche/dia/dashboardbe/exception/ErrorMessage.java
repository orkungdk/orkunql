package com.roche.dia.dashboardbe.exception;

import lombok.*;

/**
 * This class contains exception related information.
 *
 * @author orkun.gedik
 * @version 0.0.1-SNAPSHOT
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorMessage {

    private Integer status;

    private String title;

    private String details;

    private String parameter;
}
