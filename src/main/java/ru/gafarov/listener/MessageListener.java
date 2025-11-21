package ru.gafarov.listener;

public interface MessageListener {

    void sendMessageToServer(String message);

    void sendMessageToClient(String message);

}
