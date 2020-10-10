package com.scalablecapital.currencyapi.advice;

import com.scalablecapital.currencyapi.exception.CurrencyNotFoundException;
import com.scalablecapital.currencyapi.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionControllerAdvice {

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

        return ResponseEntity.status(statusCode).body(errorDto);
    }
}
