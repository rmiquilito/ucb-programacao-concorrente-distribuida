import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.1.1", 12345);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Username? ");
            String username = in.readLine();
            oos.writeObject(username);
            oos.flush();

            new Thread(() -> {
                String message;
                try {
                    while ((message = (String) ois.readObject()) != null) {
                        System.out.println(message);
                    }
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String message;
            while ((message = in.readLine()) != null) {
                oos.writeObject(message);
                oos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
