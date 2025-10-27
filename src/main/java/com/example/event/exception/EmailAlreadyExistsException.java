package com.example.event.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Пользователь с email " + email + " уже зарегистрирован");
    }
}
