import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Random;


public class MultiThreadedServer implements Runnable {
    Socket clientsocket;
    String quote = "Thou art so fair!!!";

    MultiThreadedServer(Socket clientsocket) {
        this.clientsocket = clientsocket;
    }
    public static void main(String args[]) throws Exception {
        ServerSocket sock = new ServerSocket(12346);
        System.out.println("Waiting for connection ...");

        while (true) {
            Socket client = sock.accept();
            new Thread(new MultiThreadedServer(client)).start();
        }
    }



    @Override
    public void run() {
        synchronized (this){
            try{

                System.out.println("Connected to " +
                        clientsocket.getInetAddress());
                BufferedReader from = new BufferedReader(
                        new InputStreamReader(
                                clientsocket.getInputStream()
                        )
                );

                PrintWriter to = new PrintWriter(clientsocket.getOutputStream(),
                        true);

                String request = from.readLine();
                if (!request.equals(null)){
                    System.out.println("Received quote request from client ");
                    Random rand = new Random();
                    BigInteger bigint = num_generator();

                    System.out.println(bigint);
                    int rand1 = rand.nextInt(4) + 2;
                    String stringarray = "";
                    BigInteger[] outputarray = new BigInteger[rand1];

                    for (int i=0; i <rand1; i++){

                        BigInteger rand2 = this.num_generator();
                        stringarray = stringarray.concat(rand2.toString());
                        stringarray = stringarray.concat(",");
                        outputarray[i] = rand2;
                    }
                    System.out.println("Sending" + stringarray + "to client");
                    to.println(stringarray);
                    boolean iscorrect = true;
                    for (int i = 0; i < rand1; i++){
                        String inputline = from.readLine();
                        System.out.println("Verifying factor: " + inputline + "for: " + outputarray[i]);
                        BigInteger input = new BigInteger(inputline);
                        if (outputarray[i].mod(input) == BigInteger.ZERO){
                            System.out.println("Correct!");
                        }
                        else{
                            System.out.println("Incorrect");
                            iscorrect = false;
                        }
                    }
                    if (iscorrect){
                        to.println(this.quote);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }

    public static BigInteger num_generator(){
        Random rand1 = new Random();

        BigInteger prime1 = BigInteger.probablePrime(16, rand1);
        BigInteger prime2 = BigInteger.probablePrime(16, rand1);
        BigInteger bigint = prime1.multiply(prime2);
        return bigint ;
    }
}
