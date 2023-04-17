import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String... args) {
        try (DatagramSocket sock = new DatagramSocket()) {
            sock.setSoTimeout(10000);
            InetAddress host = InetAddress.getByName(args[0]);
            int port = Integer.valueOf(args[1]);

            while(true) {
                DatagramPacket send = new DatagramPacket(new byte[1], 1, host, port); 
                sock.send(send);

                DatagramPacket recv = new DatagramPacket(new byte[1024], 1024);
                sock.receive(recv);
                System.out.println(new String(recv.getData()));
                //sock.close();
            }
        }
        catch (Exception ex) { 
            ex.printStackTrace(); 
        }
    }
}