package com.roche.dia.dashboardbe.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author orkungedik
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {

    private String secret;

    private String prefix;

    private Long tokenValidity;

}
