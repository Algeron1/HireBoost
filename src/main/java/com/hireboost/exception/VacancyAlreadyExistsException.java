package com.hireboost.exception;

public class VacancyAlreadyExistsException extends RuntimeException {

    public VacancyAlreadyExistsException(String message) {
        super(message);
    }

    public VacancyAlreadyExistsException(String message, Exception e) {
        super(message, e);
    }
}
