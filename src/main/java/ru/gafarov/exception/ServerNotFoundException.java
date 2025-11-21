package ru.gafarov.exception;

public class ServerNotFoundException extends RuntimeException {
    public ServerNotFoundException(int port) {
        super("Server on port = %d has not found".formatted(port));
    }
}
