package com.roche.dia.dashboardbe.exception;

import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Customized exception class for Roche Dia Cxim Application.
 *
 * @author orkun.gedik
 * @version 0.0.1-SNAPSHOT
 * @implNote Thanks to {@code errors}, multiple error messages can be thrown at the same time.
 * @implNote {@code httpStatusCode} calculates based on the first error message in the list.
 */
@Getter
public class ErrorException extends RuntimeException {

    private final ArrayList<ErrorMessage> errors = new ArrayList<>();
    private final int httpStatusCode;

    /**
     * Creates a basic Internal Server Error - HTTP 500
     *
     * @param cause Cause of this exception
     */
    public ErrorException(Throwable cause) {
        this(parseThrowable(cause), cause);
    }


    /**
     * Creates an exception from a list of errors The first HTTP status will be the HTTP status of the response
     *
     * @param errors list of errors to display
     * @param cause  Cause of this exception
     */
    public ErrorException(final Collection<ErrorMessage> errors, Throwable cause) {
        super(cause);
        httpStatusCode = invokeErrors(errors);
    }

    /**
     * Creates an exception from a list of errors The first HTTP status will be the HTTP status of the response
     *
     * @param errors list of errors to display
     */
    public ErrorException(final Collection<ErrorMessage> errors) {
        httpStatusCode = invokeErrors(errors);
    }

    /**
     * Create an exception from a single ErrorMessage
     *
     * @param error the error to display
     */
    public ErrorException(final ErrorMessage error) {
        this(Collections.singletonList(error));
    }

    /**
     * Create an exception from a single ErrorMessage
     *
     * @param details the error details to display
     */
    public ErrorException(ErrorType errorType, String details) {
        ErrorMessage error = new ErrorMessage();
        error.setTitle(errorType.getTitle());
        error.setStatus(errorType.getStatus());
        error.setDetails(details);

        httpStatusCode = invokeErrors(Collections.singletonList(error));
    }

    /**
     * Create an exception from a single ErrorMessage
     *
     * @param details the error details to display
     */
    public ErrorException(ErrorType errorType, String details, String parameter) {
        ErrorMessage error = new ErrorMessage();
        error.setTitle(errorType.getTitle());
        error.setStatus(errorType.getStatus());
        error.setDetails(details);
        error.setParameter(parameter);

        httpStatusCode = invokeErrors(Collections.singletonList(error));
    }

    /**
     * Create an exception from a single ErrorMessage
     *
     * @param errorType the generic error type
     */
    public ErrorException(ErrorType errorType) {
        ErrorMessage error = new ErrorMessage();
        error.setTitle(errorType.getTitle());
        error.setStatus(errorType.getStatus());

        httpStatusCode = invokeErrors(Collections.singletonList(error));
    }

    /**
     * Create an exception from a single ErrorMessage and chains the original exception to it.
     *
     * @param error the error to display
     * @param cause the Throwable that caused this exception
     */
    public ErrorException(final ErrorMessage error, Throwable cause) {
        this(Collections.singletonList(error), cause);
    }

    /**
     * @param errors
     * @return
     */
    private int invokeErrors(Collection<ErrorMessage> errors) {
        if (CollectionUtils.isEmpty(errors)) {
            // Internal Server Error is the default error
            return ErrorType.INTERNAL_SERVER_ERROR.getStatus();
        } else {
            this.errors.addAll(errors);
            // Http status code is the first Exception's http status code
            return this.errors.get(0).getStatus();
        }
    }

    /**
     * Converts throwable to more meaningful error message for the application
     *
     * @param cause Cause of the exception
     * @return {@link ErrorMessage}
     */
    private static ErrorMessage parseThrowable(Throwable cause) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setDetails(cause.getCause().getMessage());
        errorMessage.setTitle(cause.getClass().getName());
        errorMessage.setStatus(500);

        return errorMessage;
    }

    /**
     * @return the field #httpStatusCode
     **/
    public final Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * @return the field #errors
     **/
    public final List<ErrorMessage> getErrors() {
        return errors;
    }
}
