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
                BigInteger[] outputarray = new BigInteger[rand1+1];
                String stringarray = "";

                for (int i=0; i <=rand1; i++){

                    BigInteger rand2 = num_generator();
                    stringarray = stringarray.concat(rand2.toString());
                    outputarray[i] = rand2;
                    if (i != rand1){
                        stringarray = stringarray.concat(",");
                    }
                }
                System.out.println("Sending" + stringarray + "to client");
                to.println(stringarray);
                String inputline = from.readLine();
                System.out.println(inputline);
                System.out.println("Verifying factors");
                String[] inputarray = inputline.split(",");
                boolean Isvalid = true;
                boolean ifbreak = false;
                while (!ifbreak){
                    for (int i = 0; i < inputarray.length; i++){

                        BigInteger bigInteger = new BigInteger(inputarray[i]);
                        BigInteger zero = new BigInteger("0");

                        if (outputarray[i].mod(bigInteger) != zero){
                            Isvalid = false;
                        }
                    }
                    if (Isvalid) {
                        System.out.println("sending correct");
                        System.out.println("Sending incorrect");
                        System.out.println("Sending quote" + ": " +quote);
                        to.println(quote);
                        ifbreak = true;
                    }
                    else{
                        to.println("Incorrect");
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
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
