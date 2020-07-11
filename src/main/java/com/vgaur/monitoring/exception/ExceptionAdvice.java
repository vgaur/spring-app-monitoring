package com.vgaur.monitoring.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(DataLoadingError.class)
    public ResponseEntity<Object> handleDataLoadingException(DataLoadingError ex,
        WebRequest request) {

        LOGGER.error("Error - ", ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", String.format("%s -- %s", ex.getMessage(), ex.getCause().getMessage()));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleNoDataException(NoDataFoundException ex,
        WebRequest request) {
        LOGGER.error("Error - ", ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "No data found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

    }
}
