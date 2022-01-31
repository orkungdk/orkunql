package com.roche.dia.dashboardbe.validations;

import com.roche.dia.dashboardbe.exception.ErrorMessage;
import com.roche.dia.dashboardbe.exception.ErrorType;

import java.util.List;

/**
 * This class is an abstraction for validators in this project.
 *
 * @implNote All validator need to extend this class in override {@link #validate} method.
 * @implNote In case there is a blocker exception throw it instead adding a error message list.
 * @implNote {@link #validate} method returns an error message list instead throwing an exception directly.
 *           Because in this way multiple error can be thrown at the same time
 *
 * @author orkun.gedik
 * @version 0.1.1-SNAPSHOT
 */
public abstract class Validator<I> {

    /**
     * Validates the given object and returns a list of {@link ErrorMessage} if there is any error during validation.
     * @param object
     * @return
     */
    public abstract void validate(I object, List<ErrorMessage> errorMessages);

    public void addError(List<ErrorMessage> errorMessages, ErrorType errorType, String details, String parameter) {
        errorMessages.add(new ErrorMessage(errorType.getStatus(), errorType.getTitle(), details, parameter));
    }

}
