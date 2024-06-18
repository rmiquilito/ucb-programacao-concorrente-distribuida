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
        // Declara os recursos de gravar no fluxo de saída do soquete especificado, ler o fluxo de entrada do soquete especificado e cria um leitor de fluxo de caracteres correspondente à entrada do teclado
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 12345); // Cria um soquete e o conecta ao número de porta especificado no endereço IP especificado
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            // Thread para lidar com mensagens concomitantes
            new Thread(() -> {
                String message;
                Pattern pattern = Pattern.compile("\\s*.*\\?\\?"); // Compila a expressão regular em um padrão
                try {
                    while ((message = (String) ois.readObject()) != null) {
                        Matcher matcher = pattern.matcher(message); // Cria um mecanismo que executa operações de correspondência em uma sequência de caracteres interpretando o padrão
                        // Verifica as mensagens recebidas a fim de formatá-las e exibí-las adequadamente no terminal
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
                    System.exit(0); // Encerra o servidor quando se interrompe a leitura
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            String message;
            // Laço para ler o fluxo de caracteres correspondente à entrada do teclado
            while ((message = in.readLine()) != null) {
                oos.writeObject(message);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
