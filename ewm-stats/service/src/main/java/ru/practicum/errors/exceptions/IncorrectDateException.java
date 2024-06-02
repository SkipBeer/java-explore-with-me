package ru.practicum.errors.exceptions;

public class IncorrectDateException extends RuntimeException {
    public IncorrectDateException(String message) {
        super(message);
    }
}
