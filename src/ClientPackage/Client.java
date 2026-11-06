package ClientPackage;

import java.net.*;
import java.util.Scanner;

public class Client {
    static void main() {
        try (DatagramSocket socket = new DatagramSocket()){
            Scanner sc = new Scanner(System.in);

            InetAddress server = InetAddress.getByName("localhost");
            int portserver = 1234;

            System.out.println("Donner votre Nom :");
            String name = sc.nextLine();
            while (true){

                System.out.println("Ecrire votre message(ou exit pour quitter) :");
                String text = sc.nextLine();
                if (text.equalsIgnoreCase("exit")) break;

                String message = "["+name+"] :"+text;
                byte[] data = message.getBytes();

                DatagramPacket packet = new DatagramPacket(data,data.length,server,portserver);
                socket.send(packet);
            }
            System.out.println("client deconnecte");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
