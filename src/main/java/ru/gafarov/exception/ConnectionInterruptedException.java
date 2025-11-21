package ru.gafarov.exception;

public class ConnectionInterruptedException extends RuntimeException {
    public ConnectionInterruptedException(int port) {
        super("Sorry, connection on server with port = %d has interrupted".formatted(port));
    }
}
