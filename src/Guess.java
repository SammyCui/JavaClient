import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;

public class Guess implements Runnable {

    String number;
    PrintWriter to;
    BufferedReader from;
    int threadNumber;

    public Guess(String number, PrintWriter to, BufferedReader from, int threadNumber) {

        this.number = number;
        this.to = to;
        this.from = from;
        this.threadNumber = threadNumber;
    }


    @Override
    public void run() {


          //  System.out.println(this.number);

            String guesss = factorGuess(number);

            // user should enter possible responses
            //  int guesss [] = {5,2,3,4}; //TODO should be a list of numbers entered by the user


            String listGuesses = "";


            to.println(guesss);
            System.out.println("send factors " + listGuesses); //TODO send the numbers to the server

            // TODO three numbers were sent now we need to send it back to the server
            try {
                System.out.println(from.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }



    }

    public  String factorGuess (String n) {


        ArrayList<String> guesses = new ArrayList<String>();

        //    for (String n: numbers){

        ;
        BigInteger number = new BigInteger(n);
        // System.out.println("Number "+n + " and "+number);


        for (BigInteger bi = BigInteger.valueOf(2); //start from 2
             bi.compareTo(number) != 0; //stop when the numbers are equal
             bi = bi.add(BigInteger.ONE)) {

//                    System.out.println(number+" testing "+number.nextProbablePrime() );

            if (number.mod(bi) == BigInteger.ZERO) {
                System.out.println("Thread "+this.threadNumber+" Factor found " + bi);

                guesses.add(String.valueOf(bi));
                return String.valueOf(bi);
            }


        }


        //    }
        //String result[] = guesses.toArray(new String[guesses.size()]);
        System.out.println("no factors found send itself");
        return n;

    }

}
