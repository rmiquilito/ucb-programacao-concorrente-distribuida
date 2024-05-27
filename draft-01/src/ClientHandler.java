import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void sendMessage(String message) throws IOException {
        oos.writeObject(message);
    }

    public void run() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            String username = (String) ois.readObject();
            System.out.println(username + " joined");
            Server.broadcast(username + " joined", this);

            String message;
            while ((message = (String) ois.readObject()) != null) {
                String sender = "Message? " + "[" + username + "] ";
                System.out.println(sender + message);
                Server.broadcast(sender + message, this);
            }

            Server.getClients().remove(this);
            Server.broadcast(username + " left", this);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                ois.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
