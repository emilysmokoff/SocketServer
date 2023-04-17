import java.io.*;
import java.net.*;

public class SocketClient {
    public static void main(String... args) { 
        int port = Integer.valueOf(args[1]);
        String host = args[0];
        String passedInput = "";
        if(args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                passedInput = passedInput + args[i] + " ";
            }
            passedInput = passedInput + "\n";
        }

        try (Socket sock = new Socket(host, port)) {
            // Sending input down the stream
            OutputStream output = sock.getOutputStream();
            PrintWriter outputWriter = new PrintWriter(output);

            output.write(passedInput.getBytes());


            // Creating input stream and printing out output
            InputStream input = sock.getInputStream();
            int readChar = 0;
            while ((readChar = input.read()) != -1) {
                System.out.write(readChar);
            }


            //close socket
            sock.close();
        }

        //Catch exception if the socket retrieval doesn't work
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}