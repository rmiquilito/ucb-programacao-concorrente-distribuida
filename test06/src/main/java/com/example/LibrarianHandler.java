package com.example;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LibrarianHandler extends Thread {
    private Socket socket;

    public LibrarianHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // Declara os recursos de gravar no fluxo de saída do soquete especificado e ler o fluxo de entrada do soquete especificado
        try (ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream())) {
            oos.writeObject(Server.menu);
            oos.flush();
            String option;
            // Laço para ler o fluxo de entrada em busca da opção enviada
            while ((option = (String) ois.readObject()) != null) {
                switch (option) {
                    // Exibe os livros da coleção no terminal
                    case "listBooks":
                        Server.library.listBooks(oos);
                        break;

                    // Exibe os membros da coleção no terminal
                    case "listMembers":
                        Server.library.listMembers(oos);
                        break;

                    // Processa o pedido de aluguel de livro com base no nome do membro e no título do livro
                    case "rent":
                        oos.writeObject("Member name??");
                        oos.flush();
                        String name = (String) ois.readObject();
                        oos.writeObject("Book title??");
                        oos.flush();
                        String title = (String) ois.readObject();
                        Boolean response = Server.library.rentBook(title, name);
                        String message = response ? "Rented." : "Invalid."; // Calcula a resposta do pedido e exibe no terminal
                        oos.writeObject(message);
                        oos.flush();
                        break;

                    // Processa o pedido de registro de livro com base no objeto a ser traduzido em livro
                    case "register":
                        String example = "Example: {\"title\": \"Suma Teológica\", \"author\": \"Santo Tomás de Aquino\", \"genre\": \"Teologia\", \"amount\": 1}\n";
                        oos.writeObject(example + "Object??");
                        oos.flush();
                        String object = (String) ois.readObject();
                        response = Server.library.registerBook(object);
                        message = response ? "Registered." : "Invalid."; // Calcula a resposta da transcrição e exibe no terminal
                        oos.writeObject(message);
                        oos.flush();
                        break;

                    // Processa o pedido de devolução de livro com base no nome do membro e no título do livro
                    case "return":
                        oos.writeObject("Member name??");
                        oos.flush();
                        name = (String) ois.readObject();
                        oos.writeObject("Book title??");
                        oos.flush();
                        title = (String) ois.readObject();
                        response = Server.library.returnBook(title, name);
                        message = response ? "Returned." : "Invalid."; // Calcula a resposta do pedido e exibe no terminal
                        oos.writeObject(message);
                        oos.flush();
                        break;

                    case "finish":
                        System.out.println(socket + " finished.");
                        oos.writeObject(null); // Grava no fluxo de saída o valor que, ao ser lido, fará com que se interrompa a leitura
                        oos.flush();
                        return; // Retorna a execução do manipulador

                    default:
                        oos.writeObject("Invalid.");
                        oos.flush();
                        break;
                }
                oos.writeObject(Server.menu);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
