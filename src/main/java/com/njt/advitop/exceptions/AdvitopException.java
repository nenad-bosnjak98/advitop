package com.njt.advitop.exceptions;

public class AdvitopException extends RuntimeException {
    public AdvitopException(String message, Exception exception) {
        super(message,exception);
    }
    public AdvitopException(String message) {
        super(message);
    }
}
