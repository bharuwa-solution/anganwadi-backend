package com.anganwadi.anganwadi.exceptionHandler;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String messages) {
        super(messages);
    }
}
