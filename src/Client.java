import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        //System.out.print("Enter IP address: ");
        //String ip = kbd.nextLine().trim();
        String ip = "10.70.20.65"; //TODO should get input from console

        // System.out.println("hello");

        Random d = new Random();

        try {
            sock = new Socket(ip, 12346);

            //client successfully connect to server
            System.out.println("Connected to " +
                    sock.getInetAddress()); //hello



            from = new BufferedReader(new InputStreamReader(sock.getInputStream()) //TODO should handle data coming from server
            );
            to = new PrintWriter(sock.getOutputStream(), //TODO should handle data going to server
                    true);



            while (true) {
                System.out.print("Press Enter to request a quote: ");
                String enter = kbd.nextLine().trim();
                System.out.println("Requesting Quote ...");

                to.println("Request"); //send request to server

                System.out.println("Waiting ...");


                String quote =from.readLine();
                System.out.println("Data from server "+quote);

                // String response = from.readLine(); TODO should get response from server

                //should receive a random number for server
                String numbers [] = quote.split(",");  //TODO list should come from server

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


                System.out.print("Press Enter "+numbers.length+" factors: ");
                String[] guesss = kbd.nextLine().trim().split(",");

                // user should enter possible responses
                //  int guesss [] = {5,2,3,4}; //TODO should be a list of numbers entered by the user

                if (guesss.length != numbers.length){

                    //should give error that the numbers entered do not correspond to number requested
                    System.out.println("Error please enter "+numbers.length+" numbers");

                }else{
                    String listGuesses = "";

                    for (int i = 0; i < guesss.length;i++ ){

                        if (i == 0) {
                            listGuesses = String.valueOf(guesss[i]);
                        }else {
                            listGuesses = listGuesses + "," + guesss[i];
                        }
                    }

                    to.println(listGuesses);
                    System.out.println("send factors "+listGuesses); //TODO send the numbers to the server

                    // TODO three numbers were sent now we need to send it back to the server
                    System.out.println(from.readLine());
                    for (int i =0 ; i < guesss.length; i++) {

                        BigInteger bigInteger = new BigInteger(numbers[i]);
                        BigInteger bigIntegerGuess = new BigInteger(guesss[i]);
                        BigInteger zero = new BigInteger("0");
                        //check whether numbers entered are factors - should be done by server
                        if (bigInteger.mod(bigIntegerGuess) == zero) {
                            //it is a factor so keep going
                        } else {
                            //not a factor so break
                            System.out.println(guesss[i] + " is not a factor of " + numbers[i]);

                            //trygain -
                        }
                    }
                    // String s = kbd.nextLine();
                    // to.println(s);

                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public ArrayList factorGuess (String[] numbers){


        ArrayList guesses = new ArrayList<>();

        return guesses ;
    }


}
