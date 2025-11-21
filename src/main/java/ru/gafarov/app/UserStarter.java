package ru.gafarov.app;

import ru.gafarov.exception.ServerNotFoundException;
import ru.gafarov.socket.UserClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class UserStarter {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Введите номер порта к которому хотите подключиться: ");
        int port = SCANNER.nextInt();

        System.out.println("Введите ваше имя: ");
        String name = SCANNER.next();

        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), port);
            UserClient userClient = new UserClient(socket, name);
        } catch (IOException e) {
            System.err.println("Server on port = %d has not found".formatted(port));
            UserStarter.main(null);
        }
    }
}
