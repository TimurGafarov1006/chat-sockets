package ru.gafarov.exception;

public class BusyPortException extends RuntimeException {
    public BusyPortException(int port) {
        super("The port = %d is busy".formatted(port));
    }
}
