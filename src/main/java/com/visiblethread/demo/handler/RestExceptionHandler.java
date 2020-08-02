package com.visiblethread.demo.handler;

import java.time.format.DateTimeParseException;

import javax.persistence.NoResultException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.visiblethread.demo.exception.FileStorageException;
import com.visiblethread.demo.model.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler({ MaxUploadSizeExceededException.class })
    public ResponseEntity<ApiError> handleMaxUploadSizeExceededException(final MaxUploadSizeExceededException ex) {
        final ApiError apiError = new ApiError(HttpStatus.PAYLOAD_TOO_LARGE, ex.getLocalizedMessage(), "The file you are trying to upload is too large");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
	
	@ExceptionHandler({ NoResultException.class })
    public ResponseEntity<ApiError> handleNoResultsFound(final NoResultException ex) {
        final ApiError apiError = new ApiError(HttpStatus.OK, ex.getLocalizedMessage(), "Query ran successful, not data found");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
	
	@ExceptionHandler({ FileStorageException.class })
    public ResponseEntity<ApiError> handleFileStorageException(final FileStorageException ex) {
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "Error when trying to upload document");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
	
	@ExceptionHandler({ DateTimeParseException.class })
    public ResponseEntity<ApiError> handleDateTimeParseException(final DateTimeParseException ex) {
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "Error trying to format date. Please use ISO_ZONED_DATE_TIME (e.g. 2020-08-02T13:53:07.092Z)");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

	@ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiError> handleAll(final Exception ex) {
        final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "Unexpected error occurred");
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
	
}
