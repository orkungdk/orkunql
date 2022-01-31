package com.roche.dia.dashboardbe.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author orkun.gedik
 */
@Getter
@Setter
@Builder
public class LoggedInUser {

    private String email;

    private String rocheId;

    private String role;
}
