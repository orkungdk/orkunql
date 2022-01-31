package com.roche.dia.dashboardbe.validations;

import com.roche.dia.dashboardbe.exception.ErrorException;
import com.roche.dia.dashboardbe.exception.ErrorMessage;
import com.roche.dia.dashboardbe.utils.ListUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author orkun.gedik
 */
public abstract class ValidationFacade<I> {

    /**
     * Executes the given validators and returns an error list if there is any.
     *
     * @param object     to be validated object
     * @param validators the validator array
     * @return {@link List< ErrorMessage >}
     */
    public void validate(I object, Validator<I>... validators) {
        List<ErrorMessage> errorMessages = new ArrayList<>();

        ListUtils.emptyIfNull(validators).stream()
                .forEach(validator -> validator.validate(object, errorMessages));

        if (!CollectionUtils.isEmpty(errorMessages)) {
            throw new ErrorException(errorMessages);
        }
    }
}
