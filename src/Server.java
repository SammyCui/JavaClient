import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
        ServerSocket sock;
        Socket client;
        BufferedReader from;
        PrintWriter to;

        Scanner kbd = new Scanner(System.in);

        try {
            sock = new ServerSocket(12346);
            System.out.println("Waiting for connection ...");
            client = sock.accept();
            System.out.println("Connected to " +
                    client.getInetAddress());
            from = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()
                    )
            );

            to = new PrintWriter(client.getOutputStream(),
                    true);

            while (true) {
                System.out.println("me: ");
                String s = kbd.nextLine();
                to.println(s);
                String response = from.readLine();
                System.out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
