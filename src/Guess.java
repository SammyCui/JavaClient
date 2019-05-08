import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

import java.util.ArrayList;

public class Guess implements Runnable {


    int threadNumber;
    PrintWriter to;
    Socket sock;
    String[] numbers;

    public Guess(Socket socket, String[] numbers){
        threadNumber = 0;
        this.sock = socket;
        this.numbers = numbers;
    }



    @Override
    public synchronized void run() {

        String number = numbers[threadNumber];
        System.out.println(" Starting Thread "+threadNumber+ " for: "+ number);
        String guesss = this.factorGuess(number);


        // TODO three numbers were sent now we need to send it back to the server
        try {

            to = new PrintWriter(sock.getOutputStream(), //TODO should handle data going to server
                    true);


            to.println(guesss);
            System.out.println("sending factors " + guesss); //TODO send the numbers to the server

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.threadNumber ++;




    }

    public  String factorGuess (String n) {


        ArrayList<String> guesses = new ArrayList<String>();

        //    for (String n: numbers){

        ;
        BigInteger number = new BigInteger(n);
        long numberlong = number.longValue();
        // System.out.println("Number "+n + " and "+number);

        Long upperbound = this.sqrt(number).longValue();
        System.out.println(upperbound);
        for (long bi = 2; bi <=upperbound; bi++) {


                if (numberlong % bi == 0) {
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

    public BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind
        // up alternating.
        for(;;) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }

}
