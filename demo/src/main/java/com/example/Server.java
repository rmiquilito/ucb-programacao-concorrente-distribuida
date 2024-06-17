package com.example;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Server {
    public static Library library;
    public static Members members;
    public static final String menu = "[list/rent/add/return/finish]? ";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is running.");

            library = new ObjectMapper().readValue(new File("src/main/resources/books.json"), Library.class);
            // members = new ObjectMapper().readValue(new File("src/main/resources/members.json"), Members.class);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket);
                new ClientHandler(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
