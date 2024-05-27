import java.net.*;
import java.io.*;

public class DaytimeClient {
    public static void main(String[] args) {
        String hostname;
        if (args.length > 0) {
            hostname = args[0];
        } else {
            hostname = "127.0.1.1";
        }
        try {
            Socket theSocket = new Socket(hostname, 1300);
            InputStream timeStream = theSocket.getInputStream();
            StringBuffer time = new StringBuffer();
            int c;
            while ((c = timeStream.read()) != -1)
                time.append((char) c);
            String timeString = time.toString().trim();
            System.out.println("It is " + timeString + " at " + hostname);
        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
