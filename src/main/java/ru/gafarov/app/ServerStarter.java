package ru.gafarov.app;

import ru.gafarov.socket.Server;

import java.util.Scanner;

public class ServerStarter {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Введите порт для создания сервера: ");
        int port = SCANNER.nextInt();

        Server server = new Server(port);
    }
}
