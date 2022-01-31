package com.roche.dia.dashboardbe.exception;

import lombok.Getter;

/**
 * All possible expected error types in the project.
 *
 * @author orkun.gedik
 * @version 0.0.1-SNAPSHOT
 */
@Getter
public enum ErrorType {

    INVALID_BUCKET_NAME("Internal AWS S3 Bucket Name", 400),
    INVALID_FILE_NAME("Internal File Name", 400),
    INVALID_CSV_NAME("Invalid CSV NAME", 400),
    INVALID_INPUT("Invalid Input", 400),
    INVALID_REQUEST("Invalid Request", 400),
    INVALID_REQUEST_PARAMETER("Invalid Request", 400),
    MISSING_MANDATORY_FIELDS("Missing Mandatory Field(s)", 400),

    AUTHENTICATION_ERROR("Authentication Error", 401),
    FORBIDDEN("Forbidden. Authorization failed.", 403),

    DATA_DUPLICATION_ERROR("Data is already exist", 500),
    DATA_INTEGRITY_ERROR("Data Integrity Error", 500),
    UNIQUE_CONSTRAINTS_VALIDATION_ERROR("Database Unique Constraints Validation Error", 500),
    INTERNAL_SERVER_ERROR("Internal Server Error", 500);

    private String title;

    private int status;

    ErrorType(String title, int status) {
        this.title = title;
        this.status = status;
    }

}
