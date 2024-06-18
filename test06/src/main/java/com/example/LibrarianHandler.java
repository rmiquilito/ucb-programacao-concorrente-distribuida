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
                        break;

                    case "listMembers":
                        break;

                    case "rent":
                        break;

                    case "add":
                        break;

                    case "return":
                        break;

                    case "finish":
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
