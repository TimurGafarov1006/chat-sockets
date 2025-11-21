package ru.gafarov.socket;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private String name;
    private int port;

    public Client(int port, String name) {
        this.port = port;
        this.scanner = new Scanner(System.in);

        try {
            this.socket = new Socket(InetAddress.getLocalHost(), port);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            throw new RuntimeException("Такого порта не существует!");
        }

        this.name = name;
        out.println(name);
    }

    public void start()  {
        Thread reader = new Thread(this::readMessages);
        reader.start();

        String message;
        while (true) {
            message = scanner.nextLine();
            out.println(message);
        }
    }

    private void readMessages() {
        String message;
        while (true) {
            try {
                if (!((message = in.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException("Соединение было оборвано!");
            }
            System.out.println(message);
        }
    }

}
