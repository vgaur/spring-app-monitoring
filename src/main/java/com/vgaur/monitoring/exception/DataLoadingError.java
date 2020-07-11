package com.vgaur.monitoring.exception;

/**
 * Exception to indicate the data loading errors.
 */
public class DataLoadingError extends Exception {

    public DataLoadingError(String error, Throwable cause) {
        super(error, cause);
    }

}
