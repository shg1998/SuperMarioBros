/**
 * A Class which acts as the servers, reads and manipulates files from sockets and sends them to the computers
 */

package Broadcast;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class MultiServer {
    private static int port;
    public static ArrayList<String>[]data=new ArrayList[2];

    public MultiServer (int p)
    {
        port=p;
    }

    /**
     * Main method, always runs the server
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket=new ServerSocket(port);

//        ServerSocket serverSocket = new ServerSocket(port);
//        //initiallize data
//        Socket socketReader1 = serverSocket.accept();
//        ServerReader serverReader1 = new ServerReader(socketReader1,0);
//        System.out.println("first reader accepted");
//
//        Socket socketReader2 = serverSocket.accept();
//        ServerReader serverReader2 = new ServerReader(socketReader2, 1);
//        System.out.println("second reader accepted");

//        long currentTime=System.currentTimeMillis();
//        int num=0;



        while (true) {


            Socket socketReader1 = serverSocket.accept();
            ServerReader serverReader1 = new ServerReader(socketReader1);

            System.out.println("first reader accepted");

            Socket socketReader2 = serverSocket.accept();
            System.out.println("second reader accepted");
            ServerReader serverReader2 = new ServerReader(socketReader2);


            serverReader1.start();
            serverReader2.start();

            System.out.println("both reader started");


            serverReader1.join();
            serverReader2.join();


            Socket socketWriter1 = serverSocket.accept();
            ServerWriter serverWriter1 = new ServerWriter(socketWriter1);


            Socket socketWriter2 = serverSocket.accept();
            ServerWriter serverWriter2 = new ServerWriter(socketWriter2);


            System.out.println("Both readers joined");
            System.out.println("Both writers started");


            serverWriter2.start();
            serverWriter1.start();


            serverWriter1.join();
            serverWriter2.join();




            System.out.println("Both writers joined");



        }




    }

}
