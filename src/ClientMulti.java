import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class ClientMulti {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);


        //System.out.print("Enter IP address: ");
        //String ip = kbd.nextLine().trim();
        //sammy
        String ip = "10.32.95.68"; //TODO should get input from console
        //String ip = "10.32.12.115";
        // System.out.println("hello");

        Random d = new Random();

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

                System.out.println("Press Enter to request a quote: ");
                String enter = kbd.nextLine().trim();


                to.println("Request"); //send request to server




                String numberstring =from.readLine();

                //should receive a random number for server
                String numbers [] = numberstring.split(",");  //TODO list should come from server


                Guess guess = new Guess(sock, numbers);


                String listNum = "";
                for (int i = 0; i < numbers.length;i++ ){

                    if (i == 0) {
                        listNum = String.valueOf(numbers[i]);
                    }else {
                        listNum = listNum + "," + numbers[i];
                    }


                }

                String response = "Finding factors of "+listNum;
                System.out.println(response);


                //  System.out.print("Press Enter "+numbers.length+" factors: ");

                //start as many threads as there are numbers to be guessed

                for (String number : numbers){

                    Thread t = new Thread(guess);
                    t.start();
                }

                String quote = from.readLine();
                System.out.println(quote);


            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
