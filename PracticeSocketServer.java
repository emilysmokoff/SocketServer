import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class PracticeSocketServer {    

    public static void main(String... args) throws Exception {
        String[] quoteArray = {"If we can truly forgive, then we can truly be set free. - Forever my Girl", "Well, I guess that conversation that played out a million times in my head is actually about to really happen, huh? - Forever my Girl", "Thank you. For showing me how my life could be with you in it. - Forever my Girl", "Yes, Liam, I actually did something with my life after you walked out on me. - Forever my Girl", "Life is pain, Highness. Anyone who says differently is selling something. - The Princess Bride", "Death cannot stop true love. All it can do is delay it for a while. - The Princess Bride", "Why lose your venom on me? - The Princess Bride", "I always think that everything could be a trap, which is why I'm still alive. - The Princess Bride", "I just want you to be happy, even if that happy doesn't include me anymore. - The Longest Ride", "The whole time I was thinking about you - The Longest Ride", "The only thing I know for sure is if we both want to, we'll find a way to make it work. - The Longest Ride", "All I can do is promise to love you every second of every day for the rest of my life. - The Longest Ride", "Boy, I love meeting people's moms. It's like reading an instruction manual as to why they're nuts. - Ted Lasso", "Hey, takin' on a challenge is a lot like ridin' a horse. If you're comfortable while you're doing it, you're probably doin' it wrong. - Ted Lasso"};

        new Thread(() -> TCPServer(quoteArray)).start();
        new Thread(() -> UDPServer(quoteArray)).start();

    }

    public static void TCPServer (String[] quoteArray) {
        int indexOfQuote = -1;
        try {
            ServerSocket server = new ServerSocket(17);
            Socket socket = null;

            while((socket = server.accept()) != null) {
                if(indexOfQuote < quoteArray.length - 1) {
                    indexOfQuote = indexOfQuote + 1;
                } 
                else {
                    indexOfQuote = 0;
                }
                String quoteOfDay = quoteArray[indexOfQuote] + '\n';
                final Socket threadSocket = socket;
                new Thread( () -> handleTCPRequest(threadSocket, quoteOfDay)).start();
            }
            server.close();
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void handleTCPRequest(Socket socket, String quoteToPrint) {
        try {
            InputStream in = new ByteArrayInputStream(quoteToPrint.getBytes());
            OutputStream out = socket.getOutputStream();

            out.write(quoteToPrint.getBytes());

            in.close();
            out.close();
            socket.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void UDPServer (String[] quoteArray){
        int indexOfQuote = quoteArray.length - 1;
        try {
            DatagramSocket datagram = new DatagramSocket(17);
            //Boolean continue = true;
            while (true) {
                if(indexOfQuote > 0) {
                    indexOfQuote = indexOfQuote - 1;
                } 
                else {
                    indexOfQuote = quoteArray.length - 1;
                }
                String quoteOfDay = quoteArray[indexOfQuote] + '\n';

                DatagramPacket received = new DatagramPacket(new byte[1024], 1024);
                datagram.receive(received);
                InetAddress receivedAddress = received.getAddress();
                int port = received.getPort();
                //System.out.println(receivedAddress);

                DatagramPacket pack = new DatagramPacket(quoteOfDay.getBytes(), quoteOfDay.getBytes().length, receivedAddress, port);
                datagram.send(pack);
            }
            //datagram.close();
        }
        //datagram.close();

        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // public static void handleUDPServer (DatagramSocket socket, String quoteOfDay) {
    //     try {
    //         DatagramPacket pack = new DatagramPacket(quoteOfDay.getBytes(), quoteOfDay.getBytes().length, 'localhost', 17);
    //         pack.send(pack);
    //     }
    //     catch (IOException e) {
    //         System.out.println(e);
    //     }
    // }

}