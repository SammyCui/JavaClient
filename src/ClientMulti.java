import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class ClientMulti {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        String ip = "10.70.16.100"; //TODO should get input from console



        try {
            sock = new Socket(ip, 12346);

            //client successfully connect to server
            System.out.println("Connected to " +
                    sock.getInetAddress()); //hello



            from = new BufferedReader(new InputStreamReader (sock.getInputStream()) //TODO should handle data coming from server
            );
            to = new PrintWriter(sock.getOutputStream(), //TODO should handle data going to server
                    true);



            while (true) {

                System.out.print("Press <Enter> to request a quote: ");
                kbd.nextLine().trim();
                System.out.println("Requesting quote");
                to.println(); //send random request to server, to let it know we want factors

                String numbers [] = from.readLine().split(",");  //read data sent from server //should receive a random list of primes of primes from server

                Guess guess = new Guess(sock, numbers); //create object to compute the factors of the numbers provided by server, also used to synchronize

                String listNum = Arrays.toString(numbers).replace("[","").replace("]","");


                String response = "Finding factors of "+listNum;
                System.out.println(response);

                //start as many threads as there are numbers to be guessed
                for (String number : numbers){

                    Thread t = new Thread(guess); //create new thread
                    t.start();
                }

                String quote = from.readLine(); //read quote from server
                System.out.println("Received Correct from Server");
                System.out.println("Received Quote: \""+quote+"\"");

            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
