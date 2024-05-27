import java.net.*;
import java.io.*;
import java.util.Date;

public class DaytimeServer {
    public final static int DEFAULT_PORT = 1300;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
                if (port < 0 || port >= 65536) {
                    System.out.println("Port must between 0 and 65535");
                    return;
                }
            } catch (NumberFormatException e) {
            }
        }
        try {
            ServerSocket server = new ServerSocket(port);
            Socket connection = null;
            while (true) {
                System.out.println("Server pronto. Aguarda conexão.");
                try {
                    connection = server.accept();
                    OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                    Date now = new Date();
                    out.write(now.toString() + "\r\n");
                    out.flush();
                    connection.close();
                } catch (IOException e) {
                } finally {
                    try {
                        if (connection != null)
                            connection.close();
                    } catch (IOException e) {
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
