package ServerPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static void main() {
        try (DatagramSocket socket = new DatagramSocket(null)){
            socket.bind(new InetSocketAddress(1234));
            System.out.println("Serveur ecoute sur le port 1234");
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Client : " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "\nMessage : " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
