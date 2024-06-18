package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Librarian {
    public static void main(String[] args) {
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 12345);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            new Thread(() -> {
                String message;
                Pattern pattern = Pattern.compile("\\s*.*\\?\\?");
                try {
                    while ((message = (String) ois.readObject()) != null) {
                        Matcher matcher = pattern.matcher(message);
                        if (matcher.find()) {
                            System.out.print(message + "\n" + Server.prompt);
                        } else {
                            if (message.equals(Server.menu)) {
                                System.out.print(message);
                            } else {
                                System.out.println(message);
                            }
                        }
                    }
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            String message;
            while ((message = in.readLine()) != null) {
                oos.writeObject(message);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
