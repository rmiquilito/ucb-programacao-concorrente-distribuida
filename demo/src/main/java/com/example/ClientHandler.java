package com.example;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream())) {
            String username = (String) ois.readObject();
            oos.writeObject(Server.menu);
            String option;
            while ((option = (String) ois.readObject()) != null) {
                switch (option) {
                    case "list":
                        Server.library.listBooks(oos);
                        break;

                    case "rent":
                        oos.writeObject("Title? ");
                        String title = (String) ois.readObject();
                        Boolean isAvailable = Server.library.rentBook(title);
                        String message = isAvailable ? "Rented." : "Invalid.";
                        oos.writeObject(message);
                        break;

                    case "add":
                        oos.writeObject("Object? ");
                        String object = (String) ois.readObject();
                        Boolean isValid = Server.library.addBook(object);
                        message = isValid ? "Added." : "Invalid.";
                        oos.writeObject(message);
                        break;

                    case "return":
                        oos.writeObject("Title? ");
                        title = (String) ois.readObject();
                        Boolean isRentable = Server.library.returnBook(title);
                        message = isRentable ? "Returned." : "Invalid.";
                        oos.writeObject(message);
                        break;

                    case "finish":
                        System.out.println(socket + " finished.");
                        oos.writeObject(null);
                        return;
                }
                oos.writeObject(Server.menu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
