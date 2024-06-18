package com.example;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static Library library;
    public static Members members;
    public static final String prompt = "> ";
    public static final String menu = "\n" +
            "+-------------+-------------+\n" +
            "|  listBooks  | listMembers |\n" +
            "+-------------+-------------+-------------+\n" +
            "|     add     |    rent     |   return    |\n" +
            "+-------------+-------------+-------------+\n" +
            "|   finish    |\n" +
            "+-------------+\n" +
            prompt;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is running.");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println(socket + " started.");
                new LibrarianHandler(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
