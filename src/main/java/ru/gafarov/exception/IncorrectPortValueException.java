package ru.gafarov.exception;

public class IncorrectPortValueException extends RuntimeException {
    public IncorrectPortValueException() {
        super("Port has to have value between 0 and 65535");
    }
}
