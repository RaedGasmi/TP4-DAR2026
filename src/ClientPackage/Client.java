package ClientPackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    static void main() {
        try (final DatagramSocket socket = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            InetSocketAddress server = new InetSocketAddress("localhost", 1234);

            System.out.println("Donner votre Nom :");
            String name = sc.nextLine();

            Thread receiver = new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];
                    try {
                        while (!socket.isClosed()) {
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                            socket.receive(packet);
                            String msg = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                            System.out.println(msg);
                        }
                    } catch (Exception e) {
                        if (!socket.isClosed()) e.printStackTrace();
                    }
                }
            });
            receiver.setDaemon(true);
            receiver.start();

            send(socket, server, "[" + name + "] a rejoint le chat");

            System.out.println("Ecrivez vos messages (exit pour quitter) :");
            while (true) {
                String text = sc.nextLine();
                if (text.equalsIgnoreCase("exit")) {
                    send(socket, server, "[" + name + "] a quitte le chat");
                    break;
                }
                send(socket, server, "[" + name + "] : " + text);
            }
            System.out.println("client deconnecte");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void send(DatagramSocket socket, InetSocketAddress server, String message) throws Exception {
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        socket.send(new DatagramPacket(data, data.length, server));
    }
}