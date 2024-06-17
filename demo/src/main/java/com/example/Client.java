package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 12345);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Name? ");
            String name = in.readLine();
            oos.writeObject(name);
            oos.flush();

            new Thread(() -> {
                String message;
                try {
                    while ((message = (String) ois.readObject()) != null) {
                        // if (message.equals(Server.menu)) {
                        //     System.out.print(message);
                        // } else {
                        //     System.out.println(message);
                        // }
                        System.out.println(message);
                    }
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            String option;
            while ((option = in.readLine()) != null) {
                oos.writeObject(option);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
