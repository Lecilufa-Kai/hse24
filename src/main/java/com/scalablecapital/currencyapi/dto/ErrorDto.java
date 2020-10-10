package com.scalablecapital.currencyapi.dto;

public class ErrorDto {

    private int statusCode;
    private String errorType;
    private String message;
    private String field;

    public int getStatusCode() {
        return statusCode;
    }

    public ErrorDto setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getField() {
        return field;
    }

    public ErrorDto setField(String field) {
        this.field = field;
        return this;
    }

    public String getErrorType() {
        return errorType;
    }

    public ErrorDto setErrorType(String errorType) {
        this.errorType = errorType;
        return this;
    }
}
