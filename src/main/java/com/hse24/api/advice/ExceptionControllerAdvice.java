package com.hse24.api.advice;

import javax.validation.ConstraintViolationException;

import com.hse24.api.exception.CurrencyNotFoundException;
import com.hse24.api.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> handleValidationException(ConstraintViolationException ex) {

        HttpStatus statusCode = HttpStatus.BAD_REQUEST;

        final ErrorDto errorDto = buildErrorDto(statusCode.value(), null, statusCode.toString());

        ex.getConstraintViolations()
          .stream().findFirst()
          .ifPresent(violation -> errorDto.setMessage(violation.getMessage()));

        logger.warn("Data validation error", ex);
        return ResponseEntity.status(statusCode).body(errorDto);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        logger.warn("Data validation error", ex);
        return ResponseEntity.status(statusCode)
                             .body(
                                 buildErrorDto(statusCode.value(), ex.getMessage(), statusCode.toString())
                             );
    }

    @ExceptionHandler({CurrencyNotFoundException.class})
    public ResponseEntity<ErrorDto> handleCurrencyNotFoundException(CurrencyNotFoundException ex) {

        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        logger.warn("Data not found error", ex);
        return ResponseEntity.status(statusCode)
                             .body(
                                 buildErrorDto(statusCode.value(), ex.getMessage(), statusCode.toString())
                             );
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException ex) {

        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        logger.error("Internal server error", ex);
        return ResponseEntity.status(statusCode)
                             .body(
                                 buildErrorDto(statusCode.value(), ex.getMessage(), statusCode.toString())
                             );
    }

    private ErrorDto buildErrorDto(
        int statusCode,
        String message,
        String errorType
    ) {
        return new ErrorDto()
                   .setStatusCode(statusCode)
                   .setMessage(message)
                   .setErrorType(errorType);
    }
}
