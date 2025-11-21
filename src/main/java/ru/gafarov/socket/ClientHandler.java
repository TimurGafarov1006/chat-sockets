package ru.gafarov.socket;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String name;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            this.name = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SocketServer.broadcastMessage("%s присоединился к чату! | %s".formatted(
                name,
                LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
        );
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                String fullMessage = "[%s] %s | %s".formatted(
                        name,
                        message,
                        LocalDateTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                );
                SocketServer.broadcastMessage(fullMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
