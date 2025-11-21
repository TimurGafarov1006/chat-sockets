package ru.gafarov.socket;

import ru.gafarov.app.ServerStarter;
import ru.gafarov.exception.BusyPortException;
import ru.gafarov.exception.IncorrectPortValueException;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private final int port;
    private final static int MAX_ROOM_CAPACITY = 10;
    private final BlockingQueue<UserHandler> users;

    public Server(int port) {
        this.port = port;
        this.users = new LinkedBlockingQueue<>();
        try {
            startServer();
        } catch (BusyPortException | IncorrectPortValueException e) {
            System.err.println(e.getMessage());
            ServerStarter.main(null);
        }
    }

    public void notifyUsers(String message) {
        for (UserHandler userHandler : users) {
            userHandler.sendMessageToClient(message);
        }
    }

    private void startServer() throws BusyPortException, IncorrectPortValueException {
        try (ServerSocket serverSocket = new ServerSocket(port, MAX_ROOM_CAPACITY)) {
            while (true) {
                Socket socket = serverSocket.accept();
                users.add(new UserHandler(this, socket));
            }
        } catch (IOException e) {
            throw new BusyPortException(port);
        } catch (IllegalArgumentException e) {
            throw new IncorrectPortValueException();
        }
    }
}
