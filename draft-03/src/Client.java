import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        byte[] receiveData = new byte[1024];
        byte[] sendData = "great".getBytes();
        InetAddress address;

        try (DatagramSocket socket = new DatagramSocket()) {
            address = InetAddress.getLocalHost();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, 12345);
            socket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            System.out.println("Packet? " + (new String(receiveData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
