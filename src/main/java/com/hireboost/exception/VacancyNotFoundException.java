package com.hireboost.exception;

public class VacancyNotFoundException extends RuntimeException{
    public VacancyNotFoundException(String message) {
        super(message);
    }

    public VacancyNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
