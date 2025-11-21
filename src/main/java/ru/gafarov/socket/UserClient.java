package ru.gafarov.socket;

import ru.gafarov.app.UserStarter;
import ru.gafarov.exception.ConnectionInterruptedException;
import ru.gafarov.exception.ServerNotFoundException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class UserClient {

    private final static int MAX_MESSAGE_LENGTH = 50;

    private final Socket socket;
    private final Scanner scanner;
    private final BufferedReader in;
    private final PrintWriter out;
    private final String name;

    public UserClient(Socket socket, String name) {
        this.socket = socket;
        this.scanner = new Scanner(System.in);
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            throw new ServerNotFoundException(socket.getPort());
        }
        this.name = name;
        startClient();
    }

    private void startClient() {
        new Thread(() -> {
            while (true) {
                try {
                    readMessage();
                } catch (ConnectionInterruptedException e) {
                    System.err.println(e.getMessage());
                    UserStarter.main(null);
                }
            }
        }).start();

        while (true) {
            createMessage();
        }
    }

    private void readMessage() throws ConnectionInterruptedException {
        try {
            String message = in.readLine();
            System.out.println(message);
        } catch (IOException e) {
            throw new ConnectionInterruptedException(socket.getPort());
        }
    }

    private void createMessage() {
        String formattedMessage = writeMessage();
        out.println(formattedMessage);
    }

    private String writeMessage() {
        String message = scanner.nextLine();
        if (message.length() > MAX_MESSAGE_LENGTH) {
            System.out.println("Вы превысили максимальную длину сообщения в %d символов!"
                    .formatted(MAX_MESSAGE_LENGTH)
            );
            return writeMessage();
        }
        return "[%s] %s".formatted(name, message);
    }
}
