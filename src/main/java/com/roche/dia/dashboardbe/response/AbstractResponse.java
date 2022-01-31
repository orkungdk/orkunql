package com.roche.dia.dashboardbe.response;

import com.roche.dia.dashboardbe.exception.ErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * This class a wrapper for Spring's {@link ResponseEntity}.
 *
 * @author orkun.gedik
 * @version 0.0.1-SNAPSHOT
 */
public class AbstractResponse extends ResponseEntity<Object> implements Serializable {

    public AbstractResponse(HttpStatus status) {
        super(status);
    }

    public AbstractResponse(Object body, HttpStatus status) {
        super(body, status);
    }

    /**
     * Returns {@link HttpStatus#OK}
     */
    public static AbstractResponse OK() {
        return new AbstractResponse(HttpStatus.OK);
    }

    /**
     * Returns {@link HttpStatus#BAD_REQUEST}
     */
    public static AbstractResponse KO() {
        return new AbstractResponse(HttpStatus.BAD_REQUEST);
    }

    /**
     * Returns an instance of {@link AbstractResponse} with an status code depending on the given parameter.
     *
     * @param object the response object
     * @return {@link AbstractResponse}
     */
    public static AbstractResponse build(Object object) {
        if (object == null) {
            return new AbstractResponse(HttpStatus.NO_CONTENT);
        } else {
            return new AbstractResponse(object, HttpStatus.OK);
        }
    }

    /**
     * Returns an instance of {@link AbstractResponse} with an status code depending on the given parameter.
     *
     * @param e {@link ErrorException}
     * @return {@link AbstractResponse}
     */
    public static AbstractResponse error(ErrorException e) {
        return new AbstractResponse(e.getErrors(), HttpStatus.valueOf(e.getHttpStatusCode()));
    }

    /**
     * Returns an instance of {@link AbstractResponse} with an status code depending on the given parameter.
     *
     * @param objects the response objects
     * @return {@link AbstractResponse}
     */
    public static AbstractResponse build(Object... objects) {
        if (objects == null || objects.length == 0) {
            return new AbstractResponse(HttpStatus.NO_CONTENT);
        } else {
            return new AbstractResponse(objects, HttpStatus.OK);
        }
    }

    /**
     * Returns an instance of {@link AbstractResponse} with an status code depending on the given parameter.
     *
     * @param bool the boolean result
     * @return {@link AbstractResponse}
     */
    public static AbstractResponse build(boolean bool) {
        return bool ? OK() : KO();
    }
}
