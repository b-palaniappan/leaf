package io.c12.bala.web.leaf.exception;

import java.util.function.Supplier;

public class UserAlreadyExistsException extends RuntimeException implements Supplier<UserAlreadyExistsException> {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public UserAlreadyExistsException get() {
        return this;
    }
}
