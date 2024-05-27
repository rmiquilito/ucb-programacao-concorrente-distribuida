import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        byte[] sendData = "Hello, world!".getBytes();
        byte[] receiveData = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket(12345)) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            System.out.println("Packet? " + (new String(receiveData)));

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
                    receivePacket.getPort());
            socket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
