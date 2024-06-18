package com.example;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Server {
    public static Library library; // Coleção de livros. Servidor se relaciona particularmente com a biblioteca
    public static Members members; // Coleção de membros da biblioteca que realizam aluguéis e devolvem livros
    public static final String prompt = "> ";
    public static final String menu = "\n" +
            "+-------------+-------------+\n" +
            "|  listBooks  | listMembers |\n" +
            "+-------------+-------------+-------------+\n" +
            "|  register   |    rent     |   return    |\n" +
            "+-------------+-------------+-------------+\n" +
            "|   finish    |\n" +
            "+-------------+\n" +
            prompt;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) { // Cria um soquete de servidor vinculado à porta especificada
            System.out.println("Server is running.");

            //.readValue() desserializa o conteúdo JSON de um determinado arquivo para um determinado tipo Java
            library = new ObjectMapper().readValue(new File("src/main/resources/books.json"), Library.class);
            members = new ObjectMapper().readValue(new File("src/main/resources/members.json"), Members.class);

            while (true) {
                Socket socket = serverSocket.accept(); // Escuta uma conexão a ser feita a este soquete e a aceita. O método é bloqueado até que uma conexão seja feita
                System.out.println(socket + " started."); // Exibe o soquete da conexão no terminal
                new LibrarianHandler(socket).start(); // Faz com que essa thread comece a execução para lidar com o cliente em concorrência
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
