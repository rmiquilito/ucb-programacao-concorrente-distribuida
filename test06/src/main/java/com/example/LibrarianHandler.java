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
        try (ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream())) {
            oos.writeObject(Server.menu);
            oos.flush();
            String option;
            while ((option = (String) ois.readObject()) != null) {
                switch (option) {
                    case "listBooks":
                        Server.library.listBooks(oos);
                        break;

                    case "listMembers":
                        Server.library.listMembers(oos);
                        break;

                    case "rent":
                        oos.writeObject("Member name??");
                        oos.flush();
                        String name = (String) ois.readObject();
                        oos.writeObject("Book title??");
                        oos.flush();
                        String title = (String) ois.readObject();
                        Boolean response = Server.library.rentBook(title, name);
                        String message = response ? "Rented." : "Invalid.";
                        oos.writeObject(message);
                        oos.flush();
                        break;

                    case "add":
                        String example = "Example: {\"title\": \"Suma Teológica\", \"author\": \"Santo Tomás de Aquino\", \"genre\": \"Teologia\", \"amount\": 1}\n";
                        oos.writeObject(example + "Object??");
                        oos.flush();
                        String object = (String) ois.readObject();
                        response = Server.library.registerBook(object);
                        message = response ? "Added." : "Invalid.";
                        oos.writeObject(message);
                        oos.flush();
                        break;

                    case "return":
                        oos.writeObject("Member name??");
                        oos.flush();
                        name = (String) ois.readObject();
                        oos.writeObject("Book title??");
                        oos.flush();
                        title = (String) ois.readObject();
                        response = Server.library.returnBook(title, name);
                        message = response ? "Returned." : "Invalid.";
                        oos.writeObject(message);
                        oos.flush();
                        break;

                    case "finish":
                        System.out.println(socket + " finished.");
                        oos.writeObject(null);
                        oos.flush();
                        return;

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
