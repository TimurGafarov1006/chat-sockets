package ru.gafarov.socket;

import ru.gafarov.app.UserStarter;
import ru.gafarov.exception.ConnectionInterruptedException;
import ru.gafarov.exception.ServerNotFoundException;
import ru.gafarov.listener.MessageListener;

import java.io.*;
import java.net.Socket;

public class UserHandler implements MessageListener {

    private final Server server;
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public UserHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            throw new ServerNotFoundException(socket.getPort());
        }
        this.startHandler();
    }

    private void startHandler() {
        new Thread(() -> {
            while (true) {
                try {
                    sendMessageToServer(getMessageFromClient());
                } catch (ConnectionInterruptedException e) {
                    System.err.println(e.getMessage());
                    UserStarter.main(null);
                }
            }
        }).start();
    }

    private String getMessageFromClient() throws ConnectionInterruptedException {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new ConnectionInterruptedException(socket.getPort());
        }
    }

    @Override
    public void sendMessageToServer(String message) {
        server.notifyUsers(message);
    }

    @Override
    public void sendMessageToClient(String message) {
        out.println(message);
    }
}
