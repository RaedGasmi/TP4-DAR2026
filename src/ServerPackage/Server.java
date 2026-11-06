package ServerPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Server {
     static void main() {
        List<SocketAddress> clients = new ArrayList<>();
        try (DatagramSocket socket = new DatagramSocket(null)) {
            socket.bind(new InetSocketAddress(1234));
            System.out.println("Serveur ecoute sur le port 1234");
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                SocketAddress sender = packet.getSocketAddress();
                String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

                if (!clients.contains(sender)) {
                    clients.add(sender);
                    System.out.println("Nouveau client : " + sender);
                }

                System.out.println("Client : " + sender + "\nMessage : " + message);

                byte[] data = message.getBytes(StandardCharsets.UTF_8);
                for (SocketAddress client : clients) {
                    if (!client.equals(sender)) {
                        socket.send(new DatagramPacket(data, data.length, client));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}