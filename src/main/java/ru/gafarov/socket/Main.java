package ru.gafarov.socket;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите номер порта к которому подключиться: ");
        int port = scanner.nextInt();
        System.out.println("Введите имя: ");
        String name = scanner.next();
        Client client = new Client(port, name);
        client.start();
    }
}
