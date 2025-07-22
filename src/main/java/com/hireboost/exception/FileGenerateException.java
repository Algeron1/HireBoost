package com.hireboost.exception;

public class FileGenerateException extends RuntimeException{
    public FileGenerateException(String message) {
        super(message);
    }

    public FileGenerateException(String message, Exception e) {
        super(message, e);
    }
}
