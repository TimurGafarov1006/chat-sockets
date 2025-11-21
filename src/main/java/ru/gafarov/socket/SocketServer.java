package ru.gafarov.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketServer {

    private static BlockingQueue<ClientHandler> clients = new LinkedBlockingQueue<>();
    private static int port;

    public static void main(String[] args) throws IOException {
        System.out.println("Введите номер порта на котором запустим сервер: ");
        port = new Scanner(System.in).nextInt();

        try {
            ServerSocket serverSocket = new ServerSocket(port, 10);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException("Порт занят или выходит за рамки 0-65535");
        }
    }

    public static void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

}
