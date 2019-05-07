import java.io.IOException;
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

        try {

            to = new PrintWriter(sock.getOutputStream(),  //handles data coming from server
                    true);


            to.println(guesss);
            System.out.println("sending factors " + guesss);  // handle data going to server

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.threadNumber ++;

    }

    public  String factorGuess (String n) {

        ArrayList<String> guesses = new ArrayList<String>();
        BigInteger number = new BigInteger(n);
        for (BigInteger bi = BigInteger.valueOf(2); //start from 2
             bi.compareTo(number) != 0; //stop when the numbers are equal
             bi = bi.nextProbablePrime()) {

            if (number.mod(bi) == BigInteger.ZERO) {
                System.out.println("Thread "+this.threadNumber+" Factor found " + bi);

                guesses.add(String.valueOf(bi));
                return String.valueOf(bi);
            }
        }

        System.out.println("no factors found send itself");
        return n;

    }

}
