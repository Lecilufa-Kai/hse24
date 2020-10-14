package com.scalablecapital.currencyapi.advice;

import javax.validation.ConstraintViolationException;

import com.scalablecapital.currencyapi.dto.ErrorDto;
import com.scalablecapital.currencyapi.exception.CurrencyNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> handleValidationException(ConstraintViolationException ex) {

        final ErrorDto errorDto = new ErrorDto();

        HttpStatus statusCode = HttpStatus.BAD_REQUEST;

        ex.getConstraintViolations()
                .stream().findFirst()
                .ifPresent(violation -> {
                    String fieldName = getFieldName(violation.getPropertyPath().toString());
                    errorDto.setStatusCode(statusCode.value())
                            .setMessage(violation.getMessage())
                            .setField(fieldName)
                            .setErrorType(statusCode.toString());
                });

        logger.warn("Data validation error", ex);
        return ResponseEntity.status(statusCode).body(errorDto);
    }

    private String getFieldName(String filedPath) {
        return filedPath.substring(filedPath.lastIndexOf('.') + 1);
    }


    @ExceptionHandler({CurrencyNotFoundException.class})
    public ResponseEntity<ErrorDto> handleCurrencyNotFoundException(CurrencyNotFoundException ex) {

        final ErrorDto errorDto = new ErrorDto();

        HttpStatus statusCode = HttpStatus.NOT_FOUND;

        errorDto.setStatusCode(statusCode.value())
                .setMessage(ex.getMessage())
                .setErrorType(statusCode.toString());

        logger.warn("Data not found error", ex);
        return ResponseEntity.status(statusCode).body(errorDto);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDto> handleRuntimeException(RuntimeException ex) {

        final ErrorDto errorDto = new ErrorDto();

        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        errorDto.setStatusCode(statusCode.value())
                .setMessage(ex.getMessage())
                .setErrorType(statusCode.toString());

        logger.error("Internal server error", ex);
        return ResponseEntity.status(statusCode).body(errorDto);
    }
}
