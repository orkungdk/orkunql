package com.roche.dia.dashboardbe.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;
import com.roche.dia.dashboardbe.exception.ErrorException;
import com.roche.dia.dashboardbe.exception.ErrorType;
import com.roche.dia.dashboardbe.security.model.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The service implementation of the JTW tokens.
 * This service handles token related executions such as generation and validation.
 *
 * @author orkungedik
 * @version 0.0.1-SNAPSHOT
 */
@Service
public class JWTTokenService {

    @Autowired
    private JWTProperties properties;

    public String generate(String email, String rocheId, String role) {
        return insertTokenPrefix() + JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withClaim("rocheId", rocheId)
                .withExpiresAt(new Date(System.currentTimeMillis() + properties.getTokenValidity()))
                .sign(Algorithm.HMAC512(properties.getSecret()));
    }

    private String insertTokenPrefix() {
        if (Strings.isNullOrEmpty(properties.getPrefix())) {
            return "";
        } else {
            return properties.getPrefix() + " ";
        }
    }

    public LoggedInUser parse(String tokenHeader) {
        if (Strings.isNullOrEmpty(tokenHeader)) {
            return null;
        }
        DecodedJWT decodedJWT = parseSafe(tokenHeader);

        return LoggedInUser.builder()
                .email(decodedJWT.getSubject())
                .role(decodedJWT.getClaim("role").asString())
                .rocheId(decodedJWT.getClaim("rocheId").asString())
                .build();
    }

    private DecodedJWT parseSafe(String tokenHeader) {
        try {
            return JWT.require(Algorithm.HMAC512(properties.getSecret()))
                    .build()
                    .verify(tokenHeader.replace(Strings.nullToEmpty(properties.getPrefix()), ""));
        } catch (TokenExpiredException | JWTDecodeException jwtException) {
            throw new ErrorException(ErrorType.AUTHENTICATION_ERROR, jwtException.getMessage(), "token");
        }
    }
}
