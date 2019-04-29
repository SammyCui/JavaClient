import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.EventListener;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        System.out.print("Enter IP address: ");
        String ip = kbd.nextLine().trim();

        System.out.println("hello");

        try {
            sock = new Socket(ip, 12346);

            //client successfully connect to server
            System.out.println("Connected to " +
                    sock.getInetAddress()); //hello


            System.out.print("Press Enter to request a quote: ");



            from = new BufferedReader(
                    new InputStreamReader(
                            sock.getInputStream()
                    )
            );

            to = new PrintWriter(sock.getOutputStream(), true);
            while (true) {
                System.out.println("Waiting ...");
                String response = from.readLine();
                System.out.println("them: " + response);
                System.out.print("me: ");
                String s = kbd.nextLine();
                to.println(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
