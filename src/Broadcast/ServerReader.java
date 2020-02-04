package Broadcast;
/**
 * A Class to read and save files from socket
 */

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ServerReader extends Thread {


    private Socket socket;
    private InputStream input;
    private OutputStream out;
    private BufferedReader br;
    private String command = ".",tag;
    private PrintWriter pr;
    private int num;


    public ServerReader(Socket socket) {
        try {
            this.socket = socket;
            out=socket.getOutputStream();
            pr = new PrintWriter(new OutputStreamWriter(out));
            input = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run method
     */
    @Override
    public void run() {
        try {
            pr.println("ROLL");
            System.out.println("in server reader");
            identify();
            MultiServer.data[num] = new ArrayList<>();

            while (!command.equals("DONE")) {

                command = br.readLine();
                MultiServer.data[num].add(command);
                System.out.println("command : "+command);

            }

            System.out.println("out pf while");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void identify() throws Exception
    {
        System.out.println("in identify");
        tag = br.readLine();

        if(tag.equals("0"))
            num=0;

        else
            num=1;
    }

}


